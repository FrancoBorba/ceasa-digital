package br.com.uesb.ceasadigital.api.features.lista.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.lista.dto.request.ListaCreateRequestDTO;
import br.com.uesb.ceasadigital.api.features.lista.dto.response.ListaResponseDTO;
import br.com.uesb.ceasadigital.api.features.lista.mapper.ListaMapper;
import br.com.uesb.ceasadigital.api.features.lista.model.Lista;
import br.com.uesb.ceasadigital.api.features.lista.repository.ListaRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import jakarta.transaction.Transactional;

@Service
public class ListaService {

  @Autowired
  private ListaRepository listaRepository;

  @Autowired
  private ListaMapper listaMapper;

  @Autowired
  private UserService userService;

  @Transactional
  public ListaResponseDTO create(ListaCreateRequestDTO request) {
    User usuario = userService.getCurrentUser();
    Lista lista = new Lista(usuario, request.getNome());
    listaRepository.save(lista);
    return listaMapper.toResponseDTO(lista);
  }

  @Transactional
  public void delete(Long id) {
    User usuario = userService.getCurrentUser();
    Lista lista = listaRepository.findByIdAndUsuarioId(id, usuario.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
    listaRepository.delete(lista);
  }

  @Transactional
  public ListaResponseDTO findById(Long id) {
    User usuario = userService.getCurrentUser();
    Lista lista = listaRepository.findByIdAndUsuarioId(id, usuario.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
    return listaMapper.toResponseDTO(lista);
  }

  @Transactional
  public List<ListaResponseDTO> findAll() {
    User usuario = userService.getCurrentUser();
    List<Lista> listas = listaRepository.findAllByUsuarioId(usuario.getId());
    return listaMapper.toResponseDTOList(listas);
  }
}