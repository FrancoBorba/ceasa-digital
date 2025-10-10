package br.com.uesb.ceasadigital.api.features.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.uesb.ceasadigital.api.features.role.model.Role;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserRegisterDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.response.UserResponseDTO;
import br.com.uesb.ceasadigital.api.features.user.mapper.UserMapper;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;
import br.com.uesb.ceasadigital.api.features.user.repository.projections.UserDetailsProjection;

@Service
public class UserService implements UserDetailsService{

  @Autowired
  private UserRepository userRepository;

  @Autowired 
  private UserMapper mapper;
  
  @Autowired 
  private PasswordEncoder passwordEncoder;

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
    //entity.setRole(UserRole.USER);
    userRepository.save(entity); 

    var dto = mapper.toResponseDTO(entity);

    return dto;
  }
}
