package br.com.uesb.ceasadigital.api.features.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdatePasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdateRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.response.UserResponseDTO;
import br.com.uesb.ceasadigital.api.features.user.mapper.UserMapper;
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
            return userRepository.findByEmail(email).orElse(null);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getAuthenticatedUser() {
        User user = getCurrentUser();
        if (user == null) {
            throw new ResourceNotFoundException("Usuário não encontrado ou não autenticado.");
        }
        return mapper.toResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO create(UserRegisterDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DatabaseException("Email already in use.");
        }
        User entity = new User();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setTelefone(dto.getTelefone());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        Role userRole = roleRepository.findByAuthority("ROLE_USER")
                .orElseThrow(() -> new DatabaseException("Role 'ROLE_USER' não encontrada."));
        entity.addRole(userRole);
        entity.setAtivo(true);
        entity.setEmailConfirmado(false);
        
        entity = userRepository.save(entity);
        return mapper.toResponseDTO(entity);
    }

    @Transactional
    public void updatePassword(UserUpdatePasswordRequestDTO dto) {
        User currentUser = getCurrentUser();
        if (currentUser == null) throw new UsernameNotFoundException("Usuario nao autenticado.");

        if (!passwordEncoder.matches(dto.getOldPassword(), currentUser.getPassword())) {
            throw new DatabaseException("A senha antiga esta incorreta.");
        }
        currentUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(currentUser);
    }

    @Transactional
    public UserResponseDTO updateUser(UserUpdateRequestDTO dto) {
        User currentUser = getCurrentUser();
        if (currentUser == null) throw new UsernameNotFoundException("Usuario nao autenticado.");

        if (StringUtils.hasText(dto.getName())) currentUser.setName(dto.getName());
        if (StringUtils.hasText(dto.getTelefone())) currentUser.setTelefone(dto.getTelefone());
        
        // Lógica de endereço e outros campos vai aqui
        if (StringUtils.hasText(dto.getEmail()) && !currentUser.getEmail().equals(dto.getEmail())) {
             // validações de email único
             currentUser.setEmail(dto.getEmail());
        }

        User updatedUser = userRepository.save(currentUser);
        return mapper.toResponseDTO(updatedUser);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAll(String roleName, String search, Pageable pageable) {
        return userRepository.findAllByFilters(roleName, search, pageable).map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    public void confirmarEmail(User user) {
        user.setEmailConfirmado(true);
        userRepository.save(user);
    }
}