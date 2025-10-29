package br.com.uesb.ceasadigital.api.features.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.uesb.ceasadigital.api.features.auth.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(br.com.uesb.ceasadigital.api.features.user.model.User user); // Método útil para limpar tokens antigos
    void deleteByToken(String token); // Para deletar o token após uso
}