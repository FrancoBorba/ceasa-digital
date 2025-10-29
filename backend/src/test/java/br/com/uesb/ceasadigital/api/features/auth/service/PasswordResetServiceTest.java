package br.com.uesb.ceasadigital.api.features.auth.service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
// Imports da nova entidade e repositório
import br.com.uesb.ceasadigital.api.features.auth.model.PasswordResetToken;
import br.com.uesb.ceasadigital.api.features.auth.repository.PasswordResetTokenRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;
import br.com.uesb.ceasadigital.api.common.notification.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor; // Import ArgumentCaptor
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PasswordResetService Tests (Separate Token Table)")
public class PasswordResetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock // Mock para o novo repositório
    private PasswordResetTokenRepository tokenRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordResetService passwordResetService;

    private User testUser;
    private PasswordResetToken testToken; // Objeto de teste para o token
    private final String userEmail = "test@example.com";
    private final String validTokenValue = "valid-token-123";
    private final String expiredTokenValue = "expired-token-456";
    private final String invalidTokenValue = "invalid-token-789";
    private final String newPassword = "newSecurePassword";

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail(userEmail);
        testUser.setPassword("hashedOldPassword");

        // Configura um objeto PasswordResetToken válido padrão
        testToken = new PasswordResetToken(validTokenValue, LocalDateTime.now().plusHours(1), testUser);
        testToken.setId(10L); // Simula um ID do banco
    }

    @Test
    @DisplayName("forgotPassword: Should create token, save it, and send email when user exists")
    void forgotPassword_shouldCreateAndSaveTokenAndSendEmail_whenUserExists() {
        // Arrange
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(testUser));
        // Captura o token que será salvo
        ArgumentCaptor<PasswordResetToken> tokenCaptor = ArgumentCaptor.forClass(PasswordResetToken.class);

        // Act
        passwordResetService.forgotPassword(userEmail);

        // Assert
        // 1. Verifica se tokens antigos foram deletados
        verify(tokenRepository).deleteByUser(testUser);
        // 2. Verifica se um novo token foi salvo
        verify(tokenRepository).save(tokenCaptor.capture());
        PasswordResetToken savedToken = tokenCaptor.getValue();
        assertNotNull(savedToken.getToken()); // Garante que um token foi gerado
        assertEquals(testUser, savedToken.getUser()); // Verifica associação com o usuário
        assertTrue(savedToken.getExpiresAt().isAfter(LocalDateTime.now())); // Verifica data de expiração
        // 3. Verifica se o email foi enviado com o token correto
        verify(emailService).sendSimpleMail(eq(userEmail), anyString(), contains(savedToken.getToken()));
        // 4. Verifica que o usuário em si não foi salvo diretamente nesta operação
        verify(userRepository, never()).save(testUser);
    }

    @Test
    @DisplayName("forgotPassword: Should throw ResourceNotFoundException when user does not exist")
    void forgotPassword_shouldThrowException_whenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            passwordResetService.forgotPassword(userEmail);
        });

        assertTrue(exception.getMessage().contains("Usuário não encontrado com o e-mail:"));
        verify(tokenRepository, never()).deleteByUser(any()); // Não deve deletar tokens antigos
        verify(tokenRepository, never()).save(any()); // Não deve salvar novo token
        verify(emailService, never()).sendSimpleMail(anyString(), anyString(), anyString()); // Não deve enviar email
    }

    @Test
    @DisplayName("resetPassword: Should reset password, delete token, and save user when token is valid")
    void resetPassword_shouldResetPasswordDeleteTokenAndSaveUser_whenTokenIsValid() {
        // Arrange
        // 'testToken' já está configurado como válido no setUp
        when(tokenRepository.findByToken(validTokenValue)).thenReturn(Optional.of(testToken));
        when(passwordEncoder.encode(newPassword)).thenReturn("hashedNewPassword");

        // Act
        passwordResetService.resetPassword(validTokenValue, newPassword);

        // Assert
        // 1. Verifica se a senha foi codificada
        verify(passwordEncoder).encode(newPassword);
        // 2. Verifica se o usuário foi salvo com a nova senha
        verify(userRepository).save(testUser);
        assertEquals("hashedNewPassword", testUser.getPassword()); // Confirma a alteração da senha
        // 3. Verifica se o token foi deletado do repositório de tokens
        verify(tokenRepository).delete(testToken);
    }

    @Test
    @DisplayName("resetPassword: Should throw ResourceNotFoundException when token is invalid")
    void resetPassword_shouldThrowException_whenTokenIsInvalid() {
        // Arrange
        when(tokenRepository.findByToken(invalidTokenValue)).thenReturn(Optional.empty()); // Token não encontrado

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            passwordResetService.resetPassword(invalidTokenValue, newPassword);
        });

        assertTrue(exception.getMessage().contains("Token de redefinição inválido ou não encontrado"));
        verify(passwordEncoder, never()).encode(anyString()); // Não deve codificar senha
        verify(userRepository, never()).save(any()); // Não deve salvar usuário
        verify(tokenRepository, never()).delete(any()); // Não deve deletar token
    }

    @Test
    @DisplayName("resetPassword: Should throw RuntimeException and delete token when token is expired")
    void resetPassword_shouldThrowExceptionAndDeleteToken_whenTokenIsExpired() {
        // Arrange
        // Cria um token especificamente expirado para este teste
        PasswordResetToken expiredTestToken = new PasswordResetToken(expiredTokenValue, LocalDateTime.now().minusMinutes(1), testUser);
        when(tokenRepository.findByToken(expiredTokenValue)).thenReturn(Optional.of(expiredTestToken));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            passwordResetService.resetPassword(expiredTokenValue, newPassword);
        });

        assertTrue(exception.getMessage().contains("Token expirado"));
        // Verifica se o token expirado foi deletado
        verify(tokenRepository).delete(expiredTestToken);
        // Garante que a senha não foi alterada nem o usuário salvo
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("resetPassword: Should throw IllegalArgumentException when new password is too short")
    void resetPassword_shouldThrowIllegalArgumentException_whenNewPasswordIsTooShort() {
        // Arrange
        when(tokenRepository.findByToken(validTokenValue)).thenReturn(Optional.of(testToken));
        String shortPassword = "123";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            passwordResetService.resetPassword(validTokenValue, shortPassword);
        });

        assertTrue(exception.getMessage().contains("A nova senha deve ter pelo menos 6 caracteres"));
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any());
        verify(tokenRepository, never()).delete(any()); // Não deleta o token se a validação falhar
    }

    @Test
    @DisplayName("resetPassword: Should throw IllegalArgumentException when new password is null")
    void resetPassword_shouldThrowIllegalArgumentException_whenNewPasswordIsNull() {
        // Arrange
        when(tokenRepository.findByToken(validTokenValue)).thenReturn(Optional.of(testToken));
        String nullPassword = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            passwordResetService.resetPassword(validTokenValue, nullPassword);
        });

        assertTrue(exception.getMessage().contains("A nova senha deve ter pelo menos 6 caracteres"));
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any());
        verify(tokenRepository, never()).delete(any());
    }
}