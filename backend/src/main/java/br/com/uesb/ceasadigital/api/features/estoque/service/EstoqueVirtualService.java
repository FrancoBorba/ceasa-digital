package br.com.uesb.ceasadigital.api.features.estoque.service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.estoque.dto.request.EstoqueVirtualRequestDTO;
import br.com.uesb.ceasadigital.api.features.estoque.dto.response.EstoqueVirtualResponseDTO;
import br.com.uesb.ceasadigital.api.features.estoque.mapper.EstoqueVirtualMapper;
import br.com.uesb.ceasadigital.api.features.estoque.model.EstoqueVirtual;
import br.com.uesb.ceasadigital.api.features.estoque.model.enums.MetaEstoqueStatus;
import br.com.uesb.ceasadigital.api.features.estoque.repository.EstoqueVirtualRepository;
import br.com.uesb.ceasadigital.api.features.product.repository.ProductRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstoqueVirtualService {
  
  private final EstoqueVirtualRepository estoqueRepository;
  private final ProductRepository productRepository;
  private final UserService userService;
  private final EstoqueVirtualMapper estoqueMapper;
  
  public EstoqueVirtualService(EstoqueVirtualRepository estoqueRepository, ProductRepository productRepository, UserService userService, EstoqueVirtualMapper estoqueMapper) {
    this.estoqueRepository = estoqueRepository;
    this.productRepository = productRepository;
    this.userService = userService;
    this.estoqueMapper = estoqueMapper;
  }
  
  @Transactional
  public EstoqueVirtualResponseDTO create(EstoqueVirtualRequestDTO requestDTO) {
    User admin = userService.getCurrentUser();
    
    var produto = productRepository.findById(requestDTO.getProdutoId())
    .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + requestDTO.getProdutoId() + " não encontrado."));
    
    EstoqueVirtual meta = new EstoqueVirtual();
    meta.setProduto(produto);
    meta.setAdminCriador(admin);
    meta.setQuantidadeMeta(requestDTO.getQuantidadeMeta());
    meta.setStatus(MetaEstoqueStatus.ABERTA); // Status padrão
    
    var savedMeta = estoqueRepository.save(meta);
    return estoqueMapper.toResponseDTO(savedMeta);
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
    
    if (!meta.getProduto().getId().equals(requestDTO.getProdutoId())) {
      var novoProduto = productRepository.findById(requestDTO.getProdutoId())
      .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + requestDTO.getProdutoId() + " não encontrado."));
      meta.setProduto(novoProduto);
    }
    
    meta.setQuantidadeMeta(requestDTO.getQuantidadeMeta());
    
    var updatedMeta = estoqueRepository.save(meta);
    return estoqueMapper.toResponseDTO(updatedMeta);
  }
  
  @Transactional
  public EstoqueVirtualResponseDTO delete(Long id) {
    EstoqueVirtual meta = estoqueRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Meta de estoque com id " + id + " não encontrada."));
    EstoqueVirtualResponseDTO responseDTO = estoqueMapper.toResponseDTO(meta);

    // TODO: Avisar para o admin caso ja tenha um oferta de algum produtor vinculada a essa meta de estoque
    estoqueRepository.deleteById(id);
    return responseDTO;
  }
}