package br.com.uesb.ceasadigital.api.features.confirmation_token.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.features.confirmation_token.model.ConfirmationToken;
import br.com.uesb.ceasadigital.api.features.confirmation_token.repository.ConfirmationTokenRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken createConfirmationToken(User user) {
        String tokenValue = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                tokenValue,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), // Token expira em 15 minutos
                user
        );
        return confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(ConfirmationToken token) {
        token.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(token);
    }

     public void deleteToken(Long id) {
         confirmationTokenRepository.deleteById(id);
     }
}
