package br.com.uesb.ceasadigital.api.features.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;
import br.com.uesb.ceasadigital.api.features.user.repository.projections.UserDetailsProjection;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private List<UserDetailsProjection> userDetailsProjections;

    @BeforeEach
    void setUp() {
        userDetailsProjections = new ArrayList<>();
    }

    @Test
    @DisplayName("Should return user details when username is valid")
    void loadUserByUsername_WithValidUsername_ShouldReturnUserDetails() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        Long roleId = 1L;
        String authority = "ROLE_USER";

        UserDetailsProjection projection = Mockito.mock(UserDetailsProjection.class);
        when(projection.getUsername()).thenReturn(email);
        when(projection.getPassword()).thenReturn(password);
        when(projection.getRoleId()).thenReturn(roleId);
        when(projection.getAuthority()).thenReturn(authority);

        userDetailsProjections.add(projection);
        when(userRepository.searchUserAndRolesByEmail(email)).thenReturn(userDetailsProjections);

        // Act
        UserDetails result = userService.loadUserByUsername(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getUsername());
        assertEquals(password, result.getPassword());
        assertEquals(1, result.getAuthorities().size());
        assertEquals(authority, result.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    @DisplayName("Should throw username not found exception when username is invalid")
    void loadUserByUsername_WithInvalidUsername_ShouldThrowUsernameNotFoundException() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.searchUserAndRolesByEmail(email)).thenReturn(new ArrayList<>());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
            UsernameNotFoundException.class,
            () -> userService.loadUserByUsername(email)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should return user details with all roles when username is valid")
    void loadUserByUsername_WithMultipleRoles_ShouldReturnUserDetailsWithAllRoles() {
        // Arrange
        String email = "admin@example.com";
        String password = "admin123";

        UserDetailsProjection projection1 = Mockito.mock(UserDetailsProjection.class);
        when(projection1.getUsername()).thenReturn(email);
        when(projection1.getPassword()).thenReturn(password);
        when(projection1.getRoleId()).thenReturn(1L);
        when(projection1.getAuthority()).thenReturn("ROLE_USER");
        
        UserDetailsProjection projection2 = Mockito.mock(UserDetailsProjection.class);
        when(projection2.getRoleId()).thenReturn(2L);
        when(projection2.getAuthority()).thenReturn("ROLE_ADMIN");

        userDetailsProjections.add(projection1);
        userDetailsProjections.add(projection2);
        when(userRepository.searchUserAndRolesByEmail(email)).thenReturn(userDetailsProjections);

        // Act
        UserDetails result = userService.loadUserByUsername(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getUsername());
        assertEquals(password, result.getPassword());
        assertEquals(2, result.getAuthorities().size());
    }
}
