package br.com.uesb.ceasadigital.api.features.user.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import br.com.uesb.ceasadigital.api.common.exceptions.DatabaseException;
import br.com.uesb.ceasadigital.api.features.role.model.Role;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdatePasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdateRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.response.UserResponseDTO;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;
import br.com.uesb.ceasadigital.api.features.user.repository.projections.UserDetailsProjection;

@Service
public class UserService implements UserDetailsService{
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }
  
  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);
    if (result.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
    User user = new User();
    user.setEmail(result.get(0).getUsername());
    user.setPassword(result.get(0).getPassword());
    for (UserDetailsProjection projection : result) {
      user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
    }
    return user;
  }
  
  @Transactional(readOnly = true)
  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      String email = authentication.getName();
      return userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    return null;
  }
  
  @Transactional
  public void updatePassword(UserUpdatePasswordRequestDTO dto) {
    // Obtem o usuario atualmente autenticado.
    User currentUser = getCurrentUser();
    if (currentUser == null) {
      throw new UsernameNotFoundException("Usuario nao autenticado.");
    }
    
    // Valida se a senha antiga informada corresponde a senha no banco.
    if (!passwordEncoder.matches(dto.getOldPassword(), currentUser.getPassword())) {
      throw new DatabaseException("A senha antiga esta incorreta.");
    }
    
    // Criptografa e atualiza a nova senha.
    currentUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    userRepository.save(currentUser);
  }
  
  @Transactional
  public UserResponseDTO updateUser(UserUpdateRequestDTO dto) {
    // Busca o usuario autenticado que esta fazendo a requisicao.
    User currentUser = getCurrentUser();
    if (currentUser == null) {
      throw new UsernameNotFoundException("Usuario nao autenticado.");
    }
    
    // Bloco para atualizar o email, so executa se um novo email for enviado.
    if (StringUtils.hasText(dto.getEmail()) && !currentUser.getEmail().equals(dto.getEmail())) {
      // Verifica se o novo email ja esta em uso por outra conta.
      Optional<User> userWithNewEmail = userRepository.findByEmail(dto.getEmail());
      if (userWithNewEmail.isPresent()) {
        throw new DatabaseException("O email informado ja esta em uso por outra conta.");
      }
      currentUser.setEmail(dto.getEmail());
    }
    
    // Logica similar para o CPF: so atualiza se for um novo CPF.
    if (StringUtils.hasText(dto.getCpf()) && !currentUser.getCpf().equals(dto.getCpf())) {
      // Garante que o novo CPF ja nao esteja cadastrado.
      Optional<User> userWithNewCpf = userRepository.findByCpf(dto.getCpf());
      if (userWithNewCpf.isPresent()) {
        throw new DatabaseException("O CPF informado ja esta em uso por outra conta.");
      }
      currentUser.setCpf(dto.getCpf());
    }
    
    // Atualiza o nome apenas se um novo valor foi fornecido.
    if (StringUtils.hasText(dto.getName())) {
      currentUser.setName(dto.getName());
    }
    
    // Atualiza o telefone apenas se um novo valor foi fornecido.
    if (StringUtils.hasText(dto.getTelefone())) {
      currentUser.setTelefone(dto.getTelefone());
    }
    
    // Persiste todas as alteracoes feitas no objeto no banco de dados.
    User updatedUser = userRepository.save(currentUser);
    
    return new UserResponseDTO(updatedUser);
  }
}
