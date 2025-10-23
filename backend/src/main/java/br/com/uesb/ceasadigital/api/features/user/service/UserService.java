package br.com.uesb.ceasadigital.api.features.user.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.role.model.Role;
import br.com.uesb.ceasadigital.api.features.role.repository.RoleRepository;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserRegisterDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.response.UserResponseDTO;
import br.com.uesb.ceasadigital.api.features.user.mapper.UserMapper;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdatePasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdateRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;
import br.com.uesb.ceasadigital.api.features.user.repository.projections.UserDetailsProjection;

@Service
public class UserService implements UserDetailsService {
  
  @Autowired
  private UserRepository userRepository;

  @Autowired 
  private UserMapper mapper;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Autowired
  private Environment environment;

  @Autowired
  private RoleRepository roleRepository;
  
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Environment environment) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.environment = environment;
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
    Boolean isEmailConfirmado = result.get(0).getEmailConfirmado();
    user.setEmailConfirmado(isEmailConfirmado != null ? isEmailConfirmado : false); 
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
      .orElseGet(() -> {
        if (isDevOrTestProfile()) {
          return createMockUser();
        }
        throw new UsernameNotFoundException("User not found");
      });
    }
    if (isDevOrTestProfile()) {
      return createMockUser();
    }
    return null;
  }

  public UserResponseDTO create(UserRegisterDTO user){
    if (user == null) {
        throw new IllegalStateException("User cannot be null.");
    }
    if (user.getName() == null || user.getName().isBlank()) {
      throw new IllegalStateException("User name cannot be null or empty.");
    }
    if (user.getEmail() == null || user.getEmail().isBlank()) {
        throw new IllegalStateException("User email cannot be null or empty.");
    }
    if (user.getPassword() == null || user.getPassword().isBlank()) {
        throw new IllegalStateException("User password cannot be null or empty.");
    }
    if (user.getCpf() == null || user.getCpf().isBlank()){
      throw new IllegalStateException("User cpf cannot be null or empty.");
    }
    if (userRepository.findByEmail(user.getEmail()).isPresent()){
       throw new IllegalStateException("Email already in use.");
    }

    var entity = mapper.toEntity(user);
    
    // encrypting the password before saving the entity 
    String encryptedPassword = passwordEncoder.encode(entity.getPassword());
    entity.setPassword(encryptedPassword);

    Role userRole = roleRepository.findByAuthority("ROLE_USER")
        .orElseThrow(() -> new IllegalStateException("Role 'ROLE_USER' não encontrada."));
    entity.addRole(userRole); 

    entity.setAtivo(true);

    User savedUser = userRepository.save(entity); 

    return new UserResponseDTO(savedUser);
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

  
  public User findById(Long id){
    User entity = userRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

    return entity;
  }

  public void confirmarEmail(User user){
    user.setEmailConfirmado(true);
    userRepository.save(user);
  }

  
  private boolean isDevOrTestProfile() {
    String[] activeProfiles = environment.getActiveProfiles();
    for (String profile : activeProfiles) {
      if ("dev".equals(profile) || "test".equals(profile)) {
        return true;
      }
    }
    return false;
  }
  
  private User createMockUser() {
    User mockUser = new User();
    mockUser.setId(1L);
    mockUser.setName("Mock User");
    mockUser.setEmail("mock@dev.com");
    mockUser.addRole(new Role(1L, "ROLE_USER"));
    return mockUser;
  }
}
