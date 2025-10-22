package br.com.uesb.ceasadigital.api.features.endereco.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.endereco.dto.EnderecoDTO;
import br.com.uesb.ceasadigital.api.features.endereco.model.Endereco;
import br.com.uesb.ceasadigital.api.features.endereco.repository.EnderecoRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;

@Service
public class EnderecoService {

  @Autowired
  private EnderecoRepository enderecoRepository;

  @Autowired
  private UserService userService;

  @Transactional(readOnly = true)
  public List<EnderecoDTO> listarMeusEnderecos() {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    List<Endereco> enderecos = enderecoRepository.findByUsuarioId(currentUser.getId());
    return enderecos.stream()
        .map(EnderecoDTO::new)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public EnderecoDTO buscarEnderecoPorId(Long id) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    Endereco endereco = enderecoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

    if (!endereco.getUsuario().getId().equals(currentUser.getId())) {
      throw new RuntimeException("Endereço não pertence ao usuário atual");
    }

    return new EnderecoDTO(endereco);
  }

  @Transactional
  public EnderecoDTO criarEndereco(EnderecoDTO enderecoDTO) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    Endereco endereco = new Endereco();
    endereco.setUsuario(currentUser);
    endereco.setCep(enderecoDTO.getCep());
    endereco.setLogradouro(enderecoDTO.getLogradouro());
    endereco.setNumero(enderecoDTO.getNumero());
    endereco.setComplemento(enderecoDTO.getComplemento());
    endereco.setBairro(enderecoDTO.getBairro());
    endereco.setCidade(enderecoDTO.getCidade());
    endereco.setEstado(enderecoDTO.getEstado());
    endereco.setPrincipal(false);

    Endereco enderecoSalvo = enderecoRepository.save(endereco);
    return new EnderecoDTO(enderecoSalvo);
  }

  @Transactional
  public EnderecoDTO atualizarEndereco(Long id, EnderecoDTO enderecoDTO) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    Endereco endereco = enderecoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

    if (!endereco.getUsuario().getId().equals(currentUser.getId())) {
      throw new RuntimeException("Endereço não pertence ao usuário atual");
    }

    if (enderecoDTO.getCep() != null) {
      endereco.setCep(enderecoDTO.getCep());
    }
    if (enderecoDTO.getLogradouro() != null) {
      endereco.setLogradouro(enderecoDTO.getLogradouro());
    }
    if (enderecoDTO.getNumero() != null) {
      endereco.setNumero(enderecoDTO.getNumero());
    }
    if (enderecoDTO.getComplemento() != null) {
      endereco.setComplemento(enderecoDTO.getComplemento());
    }
    if (enderecoDTO.getBairro() != null) {
      endereco.setBairro(enderecoDTO.getBairro());
    }
    if (enderecoDTO.getCidade() != null) {
      endereco.setCidade(enderecoDTO.getCidade());
    }
    if (enderecoDTO.getEstado() != null) {
      endereco.setEstado(enderecoDTO.getEstado());
    }

    Endereco enderecoAtualizado = enderecoRepository.save(endereco);
    return new EnderecoDTO(enderecoAtualizado);
  }

  @Transactional
  public void deletarEndereco(Long id) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    Endereco endereco = enderecoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

    if (!endereco.getUsuario().getId().equals(currentUser.getId())) {
      throw new RuntimeException("Endereço não pertence ao usuário atual");
    }

    enderecoRepository.delete(endereco);
  }
}

