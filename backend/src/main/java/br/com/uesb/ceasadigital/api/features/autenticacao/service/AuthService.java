package br.com.uesb.ceasadigital.api.features.autenticacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.common.notification.EmailService;
import br.com.uesb.ceasadigital.api.features.confirmation_token.model.ConfirmationToken;
import br.com.uesb.ceasadigital.api.features.confirmation_token.service.ConfirmationTokenService;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;

@Service
public class AuthService {

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired 
    ConfirmationTokenService confirmationTokenService;

    public void sendConfirmationEmail(User user){
        ConfirmationToken token = confirmationTokenService.createConfirmationToken(user);

        emailService.sendConfirmationEmail(user, token.getToken(), "http://localhost:8080/auth/confirmToken?token=");
    }

    public void confirmUserEmail(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        
        User user = confirmationToken.getUser();
        
        //check if the user is already enable
        if(user.isEmailConfirmado()){
            throw new IllegalStateException("This user account is already verified and active.");
        }

        //confirm the token
        confirmationTokenService.setConfirmedAt(confirmationToken);  

        //enable the user
        userService.confirmarEmail(user);
    }
}
