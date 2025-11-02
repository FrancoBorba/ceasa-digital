package br.com.uesb.ceasadigital.api.features.produtor_produto.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.uesb.ceasadigital.api.common.exceptions.BadRequestException;
import br.com.uesb.ceasadigital.api.common.exceptions.ForbiddenException;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.common.notification.EmailService;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.product.repository.ProductRepository;
import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;
import br.com.uesb.ceasadigital.api.features.produtor.repository.ProdutorRepository;
import br.com.uesb.ceasadigital.api.features.produtor_produto.dto.request.ProdutorProdutoRequestVendaDTO;
import br.com.uesb.ceasadigital.api.features.produtor_produto.dto.response.ProdutorProdutoResponseDTO;
import br.com.uesb.ceasadigital.api.features.produtor_produto.mapper.ProdutorProdutoMapper;
import br.com.uesb.ceasadigital.api.features.produtor_produto.model.ProdutorProduto;
import br.com.uesb.ceasadigital.api.features.produtor_produto.repository.ProdutorProdutoRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import jakarta.transaction.Transactional;

@Service
public class ProdutorProdutoService {
    
    @Autowired
    UserService userService;
    
    @Autowired
    ProdutorRepository produtorRepository;
    
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    ProdutorProdutoRepository produtorProdutoRepository;
    
    @Autowired
    ProdutorProdutoMapper mapper;
    
    @Autowired
    private EmailService emailService;
    
    @Transactional
    public List<ProdutorProdutoResponseDTO> requestVenda(ProdutorProdutoRequestVendaDTO requestVendaDTO) {
        
        // pega o produtor 
        Produtor produtor = produtorRepository.findById(requestVendaDTO.getIdProdutor())
        .orElseThrow(() -> new ResourceNotFoundException(
                "Produtor com ID " + requestVendaDTO.getIdProdutor() + " não encontrado."));

        // processa a lista de IDs
        List<Long> produtosIds = requestVendaDTO.getProdutosIds();
        List<ProdutorProdutoResponseDTO> results = new ArrayList<>();

        for (Long produtoId : produtosIds) {
            // verifica se o produto existe
            Product produto = productRepository.findById(produtoId) 
            .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + produtoId + " não encontrado."));
            
            // verifica se a solicitacao ja existe
            Optional<ProdutorProduto> solicitacaoVenda = produtorProdutoRepository.findByProdutorAndProduto(produtor, produto);
            
            if (solicitacaoVenda.isPresent()) {
                ProdutorProduto produtorProduto = solicitacaoVenda.get();
                
                if (produtorProduto.getStatus().equals("STATUS_ATIVO") || produtorProduto.getStatus().equals("STATUS_PENDENTE")) {
                    results.add(mapper.toResponseDTO(produtorProduto));
                    continue;
                }
                
                // se for INATIVO/REJEITADO, reenvia a solicitação como PENDENTE
                produtorProduto.setStatus("STATUS_PENDENTE");
                ProdutorProduto updatedPp = produtorProdutoRepository.save(produtorProduto);
                results.add(mapper.toResponseDTO(updatedPp));
                
            } else { //cria uma solicitacao de venda 
                ProdutorProduto newPp = new ProdutorProduto();
                newPp.setProdutor(produtor);
                newPp.setProduto(produto);
                newPp.setStatus("STATUS_PENDENTE");
                newPp.setAdminAutorizador(null); 
                
                ProdutorProduto savedPp = produtorProdutoRepository.save(newPp);
                results.add(mapper.toResponseDTO(savedPp));
            }
        }
        
        return results;
    }
    
    @Transactional
    public List<ProdutorProdutoResponseDTO> findMySolicitacoesVenda(String status) {
        //obtem o perfil do produtor que esta logado
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new ResourceNotFoundException("Usuário não autenticado.");
        }
        
        Produtor produtor = produtorRepository.findByUsuarioId(currentUser.getId())
        .orElseThrow(() -> new IllegalStateException("O usuário autenticado não possui um perfil de produtor cadastrado."));
        
        //define os status a serem buscados
        Set<String> busca = new HashSet<>();
        if (status != null && !status.isBlank()) {
            if (status.equals("STATUS_ATIVO") || status.equals("STATUS_PENDENTE")) {
                busca.add(status);
            } else {
                return List.of();
            }
        } else {
            // Se nenhum status específico for pedido, busca ATIVO e PENDENTE por padrão
            busca.add("STATUS_ATIVO");
            busca.add("STATUS_PENDENTE");
        }
        
        //busca as solicitacoes de venda do produtor com o filtro de status
        List<ProdutorProduto> entities = produtorProdutoRepository.findAllByProdutorIdAndStatusIn(produtor.getId(), busca);
        
        return entities.stream()
        .map(mapper::toResponseDTO)
        .collect(Collectors.toList());
    }
    
    @Transactional
    public ProdutorProdutoResponseDTO desativarMinhaSolicitacaoVenda(Long id) {
        //obtem o perfil do produtor que esta logado
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new ResourceNotFoundException("Usuário não autenticado.");
        }
        
        Produtor produtor = produtorRepository.findByUsuarioId(currentUser.getId())
        .orElseThrow(() -> new IllegalStateException("O usuário autenticado não possui um perfil de produtor cadastrado."));
        
        
        // busca a solicitacao pelo id
        ProdutorProduto permission = produtorProdutoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("ProdutorProduto com ID " + id + " não encontrada."));
        
        //verifica se a solicitacao de venda pertence ao produtor logado
        if (!permission.getProdutor().getId().equals(produtor.getId())) {
            throw new IllegalStateException("Você não tem permissão para modificar esta solicitação/permissão de venda.");
        }
        
        //verifica se ja esta inativo, senao muda o status = INATIVO
        if (permission.getStatus().equals("STATUS_INATIVO")) {
            return mapper.toResponseDTO(permission);
        }
        
        permission.setStatus("STATUS_INATIVO");
        ProdutorProduto updatedSolicitacao = produtorProdutoRepository.save(permission);
        
        return mapper.toResponseDTO(updatedSolicitacao);
    }
    
    public List<ProdutorProdutoResponseDTO> findAll(String status) { 
        List<ProdutorProduto> entities;
        
        if (status != null && !status.isBlank()) {
            // Se um status for fornecido, filtra por ele.
            entities = produtorProdutoRepository.findAllByStatus(status.toUpperCase());
        } else {
            // Se nenhum status for fornecido, retorna todas as permissões.
            entities = produtorProdutoRepository.findAll(); 
        }
        
        return entities.stream()
        .map(mapper::toResponseDTO) 
        .collect(Collectors.toList());
    }
    
    @Transactional
    public ProdutorProdutoResponseDTO approveOrRejectVenda(Long id, String novoStatus) {
        // Obtem o usuário Admin autenticado
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new ResourceNotFoundException("Usuário admin não autenticado.");
        }
        
        if (!currentUser.hasRole("ROLE_ADMIN")) {
            throw new ForbiddenException("Apenas administradores podem aprovar ou rejeitar solicitações.");
        }
        
        // Busca a solicitação pelo id
        ProdutorProduto solicitacao = produtorProdutoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Solicitação ProdutorProduto com ID " + id + " não encontrada."));
        
        // Valida o novo status
        if (!"STATUS_ATIVO".equalsIgnoreCase(novoStatus) && !"STATUS_REJEITADO".equalsIgnoreCase(novoStatus)) {
            throw new BadRequestException("Status inválido. Use 'STATUS_ATIVO' ou 'STATUS_REJEITADO'.");
        }
        
        solicitacao.setStatus(novoStatus.toUpperCase());
        solicitacao.setAdminAutorizador(currentUser);
        
        ProdutorProduto updatedSolicitacao = produtorProdutoRepository.save(solicitacao);
        
        try {
            // Obter o usuário (produtor) associado à solicitação
            User produtorUser = updatedSolicitacao.getProdutor().getUsuario();
            Product produto = updatedSolicitacao.getProduto();
            
            // Enviar o e-mail
            emailService.sendSolicitacaoVendaStatusEmail(produtorUser, produto, novoStatus.toUpperCase());
            
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail de status da solicitação: " + e.getMessage());
        }
        
        return mapper.toResponseDTO(updatedSolicitacao);
    }
}
