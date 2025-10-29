package br.com.uesb.ceasadigital.api.features.auth.service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.auth.model.PasswordResetToken; 
import br.com.uesb.ceasadigital.api.features.auth.repository.PasswordResetTokenRepository; 
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;
import br.com.uesb.ceasadigital.api.common.notification.EmailService; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository; 

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o e-mail: " + email));

        // Limpa tokens antigos do usuário (opcional, mas bom para evitar acúmulo)
        tokenRepository.deleteByUser(user);

        String tokenValue = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);

        // Cria e salva a entidade PasswordResetToken
        PasswordResetToken resetToken = new PasswordResetToken(tokenValue, expiresAt, user);
        tokenRepository.save(resetToken);

        // Cria o link e envia o e-mail (usando sendSimpleMail)
        String resetLink = "http://localhost:3000/reset-password?token=" + tokenValue; // Ajuste a URL base se necessário
        String emailSubject = "CEASA Digital - Redefinição de Senha";
         String emailBody = "Olá " + user.getName() + ",\n\n"
                          + "Você solicitou a redefinição de sua senha.\n"
                          + "Clique no link abaixo para criar uma nova senha:\n"
                          + resetLink + "\n\n"
                          + "Se você não solicitou isso, por favor ignore este e-mail.\n"
                          + "O link expirará em 1 hora.\n\n"
                          + "Atenciosamente,\nEquipe CEASA Digital";
        emailService.sendSimpleMail(user.getEmail(), emailSubject, emailBody);
    }

    @Transactional
    public void resetPassword(String tokenValue, String newPassword) {
        // Busca o token na nova tabela
        PasswordResetToken resetToken = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new ResourceNotFoundException("Token de redefinição inválido ou não encontrado."));

        // Verifica se o token expirou
        if (resetToken.getExpiresAt() == null || resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken); // Remove o token expirado
            throw new RuntimeException("Token expirado. Por favor, solicite a redefinição novamente.");
        }

        // Verifica a nova senha
         if (newPassword == null || newPassword.length() < 6) {
             throw new IllegalArgumentException("A nova senha deve ter pelo menos 6 caracteres.");
         }

        // Obtém o usuário associado ao token
        User user = resetToken.getUser();
        if (user == null) {
             // Segurança extra: se o usuário associado não existir mais
             tokenRepository.delete(resetToken);
             throw new ResourceNotFoundException("Usuário associado ao token não encontrado.");
        }

        // Atualiza a senha do usuário
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Remove o token da tabela após o uso bem-sucedido
        tokenRepository.delete(resetToken);
    }
}