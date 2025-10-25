package br.com.uesb.ceasadigital.api.features.auth.service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService; // Assume EmailService.java foi criado como no passo anterior

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o PasswordEncoder (já deve estar configurado no seu projeto Spring Security)

    @Transactional // Adiciona transação para garantir atomicidade
    public void forgotPassword(String email) {
        // 1. Busca o usuário pelo e-mail
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o e-mail: " + email)); // Mensagem mais específica

        // 2. Gera um token único
        String token = UUID.randomUUID().toString();

        // 3. Define o token e a data de expiração (ex: 1 hora a partir de agora)
        user.setResetToken(token);
        user.setResetTokenExpires(LocalDateTime.now().plusHours(1));

        // 4. Salva as alterações no usuário (token e expiração)
        userRepository.save(user);

        // 5. Cria o link de redefinição (ajuste a URL base conforme necessário)
        // TODO: Idealmente, a URL base deveria vir de uma configuração (application.yml ou .env)
        String resetLink = "http://localhost:3000/reset-password?token=" + token; // Exemplo para frontend rodando na porta 3000

        // 6. Envia o e-mail (ou loga no console, dependendo da configuração)
        String emailSubject = "CEASA Digital - Redefinição de Senha";
        String emailBody = "Olá " + user.getName() + ",\n\n"
                         + "Você solicitou a redefinição de sua senha.\n"
                         + "Clique no link abaixo para criar uma nova senha:\n"
                         + resetLink + "\n\n"
                         + "Se você não solicitou isso, por favor ignore este e-mail.\n"
                         + "O link expirará em 1 hora.\n\n"
                         + "Atenciosamente,\nEquipe CEASA Digital";

        emailService.sendEmail(user.getEmail(), emailSubject, emailBody);
    }

    @Transactional // Adiciona transação
    public void resetPassword(String token, String newPassword) {
        // 1. Busca o usuário pelo token
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token de redefinição inválido ou não encontrado."));

        // 2. Verifica se o token expirou
        if (user.getResetTokenExpires() == null || user.getResetTokenExpires().isBefore(LocalDateTime.now())) {
            // Invalida o token expirado para segurança
            user.setResetToken(null);
            user.setResetTokenExpires(null);
            userRepository.save(user);
            throw new RuntimeException("Token expirado. Por favor, solicite a redefinição novamente."); // Mensagem mais clara
        }

        // 3. Verifica se a nova senha é válida (adicione mais validações se necessário)
        if (newPassword == null || newPassword.length() < 6) {
             throw new IllegalArgumentException("A nova senha deve ter pelo menos 6 caracteres.");
        }

        // 4. Atualiza a senha (com hash)
        user.setPassword(passwordEncoder.encode(newPassword));

        // 5. Invalida o token após o uso
        user.setResetToken(null);
        user.setResetTokenExpires(null);

        // 6. Salva o usuário com a nova senha e token invalidado
        userRepository.save(user);
    }
}