package br.com.uesb.ceasadigital.api.features.estoque.service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.estoque.dto.request.EstoqueVirtualRequestDTO;
import br.com.uesb.ceasadigital.api.features.estoque.dto.response.EstoqueVirtualResponseDTO;
import br.com.uesb.ceasadigital.api.features.estoque.mapper.EstoqueVirtualMapper;
import br.com.uesb.ceasadigital.api.features.estoque.model.EstoqueVirtual;
import br.com.uesb.ceasadigital.api.features.estoque.model.enums.MetaEstoqueStatus;
import br.com.uesb.ceasadigital.api.features.estoque.repository.EstoqueVirtualRepository;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.enums.OfertaStatus;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.repository.OfertaProdutorRepository;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.product.repository.ProductRepository;
import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;
import br.com.uesb.ceasadigital.api.features.produtor_produto.model.ProdutorProduto;
import br.com.uesb.ceasadigital.api.features.produtor_produto.repository.ProdutorProdutoRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class EstoqueVirtualService {
  
  private final EstoqueVirtualRepository estoqueRepository;
  private final ProductRepository productRepository;
  private final UserService userService;
  private final EstoqueVirtualMapper estoqueMapper;
  private final ProdutorProdutoRepository produtorProdutoRepository;
  private final OfertaProdutorRepository ofertaProdutorRepository;
  
  public EstoqueVirtualService(
  EstoqueVirtualRepository estoqueRepository, 
  ProductRepository productRepository, 
  UserService userService, 
  EstoqueVirtualMapper estoqueMapper,
  ProdutorProdutoRepository produtorProdutoRepository,
  OfertaProdutorRepository ofertaProdutorRepository
  ) {
    this.estoqueRepository = estoqueRepository;
    this.productRepository = productRepository;
    this.userService = userService;
    this.estoqueMapper = estoqueMapper;
    this.produtorProdutoRepository = produtorProdutoRepository;
    this.ofertaProdutorRepository = ofertaProdutorRepository;
  }
  
  @Transactional
  public EstoqueVirtualResponseDTO create(EstoqueVirtualRequestDTO requestDTO) {
    User admin = userService.getCurrentUser();
    
    Product produto = productRepository.findById(requestDTO.getProdutoId())
    .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + requestDTO.getProdutoId() + " não encontrado."));
    
    EstoqueVirtual meta = new EstoqueVirtual();
    meta.setProduto(produto);
    meta.setAdminCriador(admin);
    meta.setQuantidadeMeta(requestDTO.getQuantidadeMeta());
    meta.setStatus(MetaEstoqueStatus.ABERTA);
    
    EstoqueVirtual savedMeta = estoqueRepository.save(meta);
    
    distribuirMetaParaProdutores(savedMeta);
    
    return estoqueMapper.toResponseDTO(savedMeta);
  }
  
  /**
  * Método auxiliar para criar e distribuir Ofertas de Produtor
  * baseado em uma Meta de Estoque recém-criada ou atualizada.
  */
  private void distribuirMetaParaProdutores(EstoqueVirtual meta) {
    Product produto = meta.getProduto();
    BigDecimal totalMeta = meta.getQuantidadeMeta();
    
    // 1. Encontrar todos os produtores que podem vender este produto
    List<ProdutorProduto> permissoes = produtorProdutoRepository
    .findAllByProdutoIdAndStatus(produto.getId(), "STATUS_ATIVO");
    
    if (permissoes.isEmpty()) {
      return;
    }
    
    // 2. Calcular a divisão igualitária
    int numProdutores = permissoes.size();
    BigDecimal quantidadePorProdutor = totalMeta.divide(
    new BigDecimal(numProdutores), 
    3, 
    RoundingMode.HALF_UP
    );
    
    // 3. Criar uma OfertaProdutor "PENDENTE" para cada um
    for (ProdutorProduto permissao : permissoes) {
      Produtor produtor = permissao.getProdutor();
      
      OfertaProdutor novaOferta = new OfertaProdutor();
      novaOferta.setMetaEstoque(meta);
      novaOferta.setProdutor(produtor);
      novaOferta.setQuantidadeOfertada(quantidadePorProdutor);
      novaOferta.setQuantidadeDisponivel(quantidadePorProdutor);
      novaOferta.setTotalVolumeVendido(BigDecimal.ZERO);
      novaOferta.setStatus(OfertaStatus.PENDENTE); 
      
      ofertaProdutorRepository.save(novaOferta);
    }
  }
  
  @Transactional(readOnly = true)
  public EstoqueVirtualResponseDTO findById(Long id) {
    var meta = estoqueRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Meta de estoque com id " + id + " não encontrada."));
    return estoqueMapper.toResponseDTO(meta);
  }
  
  @Transactional(readOnly = true)
  public List<EstoqueVirtualResponseDTO> findAll() {
    return estoqueMapper.toResponseDTOList(estoqueRepository.findAll());
  }
  
  @Transactional
  public EstoqueVirtualResponseDTO update(Long id, EstoqueVirtualRequestDTO requestDTO) {
    var meta = estoqueRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Meta de estoque com id " + id + " não encontrada."));
    
    // VERIFICAÇÃO DE MUDANÇAS
    boolean produtoMudou = !meta.getProduto().getId().equals(requestDTO.getProdutoId());
    boolean quantidadeMudou = meta.getQuantidadeMeta().compareTo(requestDTO.getQuantidadeMeta()) != 0;
    
    // Se nada mudou, retorna o objeto atual sem fazer nada
    if (!produtoMudou && !quantidadeMudou) {
      return estoqueMapper.toResponseDTO(meta);
    }
    
    // So limpa as ofertas antigas se realmente formos atualizar
    if (meta.getOfertas() != null) {
      meta.getOfertas().clear();
    }
    
    // ATUALIZAÇÃO DOS CAMPOS
    if (produtoMudou) {
      var novoProduto = productRepository.findById(requestDTO.getProdutoId())
      .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + requestDTO.getProdutoId() + " não encontrado."));
      meta.setProduto(novoProduto);
    }
    
    meta.setQuantidadeMeta(requestDTO.getQuantidadeMeta());
    
    // Salva as mudancas
    var updatedMeta = estoqueRepository.saveAndFlush(meta);
    
    // Gera novas ofertas baseadas na nova configuração
    distribuirMetaParaProdutores(updatedMeta);
    
    return estoqueMapper.toResponseDTO(updatedMeta);
  }
  
  @Transactional
  public EstoqueVirtualResponseDTO delete(Long id) {
    EstoqueVirtual meta = estoqueRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Meta de estoque com id " + id + " não encontrada."));
    EstoqueVirtualResponseDTO responseDTO = estoqueMapper.toResponseDTO(meta);
    estoqueRepository.deleteById(id);
    return responseDTO;
  }
}