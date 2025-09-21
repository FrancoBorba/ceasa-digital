package br.com.uesb.ceasadigital.api.features.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

        UserDetailsProjection projection = new UserDetailsProjection() {
            @Override
            public String getUsername() {
                return email;
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public Long getRoleId() {
                return roleId;
            }

            @Override
            public String getAuthority() {
                return authority;
            }
        };

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

        UserDetailsProjection projection1 = createUserDetailsProjection(email, password, 1L, "ROLE_USER");
        UserDetailsProjection projection2 = createUserDetailsProjection(email, password, 2L, "ROLE_ADMIN");

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

    private UserDetailsProjection createUserDetailsProjection(String username, String password, Long roleId, String authority) {
        return new UserDetailsProjection() {
            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public Long getRoleId() {
                return roleId;
            }

            @Override
            public String getAuthority() {
                return authority;
            }
        };
    }
}
