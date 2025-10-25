package br.com.uesb.ceasadigital.api.features.auth.service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*; // Import static methods like assertThrows, assertNotNull, etc.
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*; // Import static methods like verify, when, etc.

@ExtendWith(MockitoExtension.class) // Habilita as anotações do Mockito
@DisplayName("PasswordResetService Tests")
public class PasswordResetServiceTest {

    @Mock // Cria um mock para o UserRepository
    private UserRepository userRepository;

    @Mock // Cria um mock para o EmailService
    private EmailService emailService;

    @Mock // Cria um mock para o PasswordEncoder
    private PasswordEncoder passwordEncoder;

    @InjectMocks // Cria uma instância de PasswordResetService injetando os mocks acima
    private PasswordResetService passwordResetService;

    private User testUser;
    private final String userEmail = "test@example.com";
    private final String validToken = "valid-token-123";
    private final String expiredToken = "expired-token-456";
    private final String invalidToken = "invalid-token-789";
    private final String newPassword = "newSecurePassword";

    @BeforeEach
    void setUp() {
        // Configura um usuário de teste padrão antes de cada teste
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail(userEmail);
        testUser.setPassword("hashedOldPassword"); // Senha antiga com hash
    }

    @Test
    @DisplayName("forgotPassword: Should generate token, save user, and send email when user exists")
    void forgotPassword_shouldGenerateTokenSaveUserAndSendEmail_whenUserExists() {
        // Arrange: Configura o mock do repositório para retornar o usuário quando buscado por e-mail
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(testUser));

        // Act: Chama o método a ser testado
        passwordResetService.forgotPassword(userEmail);

        // Assert: Verifica as interações esperadas
        // 1. Verifica se o método save foi chamado no repositório (para salvar token e expiração)
        verify(userRepository).save(any(User.class));
        // 2. Verifica se o resetToken e resetTokenExpires foram definidos no objeto user (antes de salvar)
        assertNotNull(testUser.getResetToken());
        assertNotNull(testUser.getResetTokenExpires());
        assertTrue(testUser.getResetTokenExpires().isAfter(LocalDateTime.now())); // Verifica se a expiração é no futuro
        // 3. Verifica se o método sendEmail foi chamado no EmailService
        verify(emailService).sendEmail(eq(userEmail), anyString(), contains(testUser.getResetToken()));
    }

    @Test
    @DisplayName("forgotPassword: Should throw ResourceNotFoundException when user does not exist")
    void forgotPassword_shouldThrowException_whenUserDoesNotExist() {
        // Arrange: Configura o mock para retornar vazio (usuário não encontrado)
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        // Act & Assert: Verifica se a exceção ResourceNotFoundException é lançada
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            passwordResetService.forgotPassword(userEmail);
        });

        // Verifica a mensagem da exceção
        assertTrue(exception.getMessage().contains("Usuário não encontrado com o e-mail:"));
        // Verifica que save e sendEmail NÃO foram chamados
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("resetPassword: Should reset password, clear token, and save user when token is valid and not expired")
    void resetPassword_shouldResetPasswordClearTokenAndSaveUser_whenTokenIsValidAndNotExpired() {
        // Arrange: Configura o usuário com um token válido e data de expiração futura
        testUser.setResetToken(validToken);
        testUser.setResetTokenExpires(LocalDateTime.now().plusHours(1));
        // Configura o mock do repositório para retornar este usuário quando buscado pelo token
        when(userRepository.findByResetToken(validToken)).thenReturn(Optional.of(testUser));
        // Configura o mock do passwordEncoder para simular o hash da nova senha
        when(passwordEncoder.encode(newPassword)).thenReturn("hashedNewPassword");

        // Act: Chama o método de redefinição de senha
        passwordResetService.resetPassword(validToken, newPassword);

        // Assert: Verifica as interações e o estado final do usuário
        // 1. Verifica se o passwordEncoder.encode foi chamado com a nova senha
        verify(passwordEncoder).encode(newPassword);
        // 2. Verifica se o método save foi chamado no repositório
        verify(userRepository).save(any(User.class));
        // 3. Verifica se a senha do usuário foi atualizada para o novo hash
        assertEquals("hashedNewPassword", testUser.getPassword());
        // 4. Verifica se o token e a data de expiração foram limpos (invalidados)
        assertNull(testUser.getResetToken());
        assertNull(testUser.getResetTokenExpires());
    }

    @Test
    @DisplayName("resetPassword: Should throw ResourceNotFoundException when token is invalid")
    void resetPassword_shouldThrowException_whenTokenIsInvalid() {
        // Arrange: Configura o mock para retornar vazio (token não encontrado)
        when(userRepository.findByResetToken(invalidToken)).thenReturn(Optional.empty());

        // Act & Assert: Verifica se a exceção ResourceNotFoundException é lançada
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            passwordResetService.resetPassword(invalidToken, newPassword);
        });

        // Verifica a mensagem
        assertTrue(exception.getMessage().contains("Token de redefinição inválido ou não encontrado"));
        // Garante que o encode e save não foram chamados
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("resetPassword: Should throw RuntimeException, clear token, and save user when token is expired")
    void resetPassword_shouldThrowExceptionClearTokenAndSaveUser_whenTokenIsExpired() {
        // Arrange: Configura o usuário com um token válido mas data de expiração no passado
        testUser.setResetToken(expiredToken);
        testUser.setResetTokenExpires(LocalDateTime.now().minusMinutes(1)); // Expirou 1 minuto atrás
        // Configura o mock para retornar este usuário
        when(userRepository.findByResetToken(expiredToken)).thenReturn(Optional.of(testUser));

        // Act & Assert: Verifica se a RuntimeException é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            passwordResetService.resetPassword(expiredToken, newPassword);
        });

        // Verifica a mensagem
        assertTrue(exception.getMessage().contains("Token expirado"));
        // Verifica se o save FOI chamado (para limpar o token expirado)
        verify(userRepository).save(any(User.class));
        // Verifica se o token e a expiração foram realmente limpos no objeto user antes do save
        assertNull(testUser.getResetToken());
        assertNull(testUser.getResetTokenExpires());
        // Garante que o encode da nova senha NÃO foi chamado
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    @DisplayName("resetPassword: Should throw IllegalArgumentException when new password is too short")
    void resetPassword_shouldThrowIllegalArgumentException_whenNewPasswordIsTooShort() {
        // Arrange: Configura token válido
        testUser.setResetToken(validToken);
        testUser.setResetTokenExpires(LocalDateTime.now().plusHours(1));
        when(userRepository.findByResetToken(validToken)).thenReturn(Optional.of(testUser));
        String shortPassword = "123"; // Senha curta

        // Act & Assert: Verifica se IllegalArgumentException é lançada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            passwordResetService.resetPassword(validToken, shortPassword);
        });

        // Verifica a mensagem
        assertTrue(exception.getMessage().contains("A nova senha deve ter pelo menos 6 caracteres"));
        // Garante que o encode e save não foram chamados
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("resetPassword: Should throw IllegalArgumentException when new password is null")
    void resetPassword_shouldThrowIllegalArgumentException_whenNewPasswordIsNull() {
        // Arrange: Configura token válido
        testUser.setResetToken(validToken);
        testUser.setResetTokenExpires(LocalDateTime.now().plusHours(1));
        when(userRepository.findByResetToken(validToken)).thenReturn(Optional.of(testUser));
        String nullPassword = null; // Senha nula

        // Act & Assert: Verifica se IllegalArgumentException é lançada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            passwordResetService.resetPassword(validToken, nullPassword);
        });

        // Verifica a mensagem
        assertTrue(exception.getMessage().contains("A nova senha deve ter pelo menos 6 caracteres"));
        // Garante que o encode e save não foram chamados
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}