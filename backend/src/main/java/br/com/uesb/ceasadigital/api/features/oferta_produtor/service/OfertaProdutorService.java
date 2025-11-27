package br.com.uesb.ceasadigital.api.features.oferta_produtor.service;

import br.com.uesb.ceasadigital.api.common.exceptions.BadRequestException;
import br.com.uesb.ceasadigital.api.common.exceptions.ForbiddenException;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.dto.response.OfertaProdutorResponseDTO;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.mapper.OfertaProdutorMapper;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.enums.OfertaStatus;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.repository.OfertaProdutorRepository;
import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;
import br.com.uesb.ceasadigital.api.features.produtor.repository.ProdutorRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OfertaProdutorService {

  @Autowired
  private EstoqueService estoqueService;
  
  @Autowired
  private OfertaProdutorRepository ofertaProdutorRepository;
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private ProdutorRepository produtorRepository;
  
  @Autowired
  private OfertaProdutorMapper mapper;
  
  /**
  * Busca o perfil de Produtor do usuário autenticado.
  */
  private Produtor getCurrentProdutor() {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new ResourceNotFoundException("Usuário não autenticado.");
    }
    return produtorRepository.findByUsuarioId(currentUser.getId())
    .orElseThrow(() -> new ForbiddenException("Usuário atual não é um produtor."));
  }
  
  /**
  * Lista todas as ofertas (metas) atribuídas ao produtor logado.
  * Pode filtrar por status (PENDENTE, ATIVO, etc.)
  */
  @Transactional(readOnly = true)
  public List<OfertaProdutorResponseDTO> listarMinhasOfertas(String status) {
    Produtor produtor = getCurrentProdutor();
    List<OfertaProdutor> ofertas;
    
    if (StringUtils.hasText(status)) {
      try {
        OfertaStatus statusEnum = OfertaStatus.valueOf(status.toUpperCase());
        ofertas = ofertaProdutorRepository.findByProdutorIdAndStatus(produtor.getId(), statusEnum);
      } catch (IllegalArgumentException e) {
        throw new BadRequestException("Status inválido: " + status);
      }
    } else {
      ofertas = ofertaProdutorRepository.findByProdutorId(produtor.getId());
    }
    
    return mapper.toResponseDTOList(ofertas);
  }
  
  /**
  * Produtor confirma uma oferta PENDENTE, mudando seu status para ATIVA.
  */
  @Transactional
  public OfertaProdutorResponseDTO confirmarOferta(Long ofertaId) {
    Produtor produtor = getCurrentProdutor();
    BigDecimal quantidade = BigDecimal.valueOf(1); // Reservando 1 unidade ao confirmar a oferta
    
    OfertaProdutor oferta = ofertaProdutorRepository.findById(ofertaId)
    .orElseThrow(() -> new ResourceNotFoundException("Oferta com ID " + ofertaId + " não encontrada."));
    
    // Validação de segurança: A oferta pertence a este produtor?
    if (!oferta.getProdutor().getId().equals(produtor.getId())) {
      throw new ForbiddenException("Esta oferta não pertence a você.");
    }
    
    // Validação de regra: Só pode confirmar se estiver PENDENTE
    if (oferta.getStatus() != OfertaStatus.PENDENTE) {
      throw new BadRequestException("Esta oferta não pode ser confirmada (Status atual: " + oferta.getStatus() + ").");
    }
    
    oferta.setStatus(OfertaStatus.ATIVA);
    OfertaProdutor ofertaSalva = ofertaProdutorRepository.save(oferta);

    estoqueService.reservarProdutos(ofertaId, quantidade);
    
    return mapper.toResponseDTO(ofertaSalva);
  }
}