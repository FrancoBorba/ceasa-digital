package br.com.uesb.ceasadigital.api.features.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.uesb.ceasadigital.api.features.role.model.Role;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;
import br.com.uesb.ceasadigital.api.features.user.repository.projections.UserDetailsProjection;

@Service
public class UserService implements UserDetailsService{

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private Environment environment;

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
