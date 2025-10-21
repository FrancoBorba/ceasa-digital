package br.com.uesb.ceasadigital.api.features.autenticacao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.uesb.ceasadigital.api.common.notification.EmailService;
import br.com.uesb.ceasadigital.api.features.autenticacao.service.AuthService;
import br.com.uesb.ceasadigital.api.features.confirmation_token.model.ConfirmationToken;
import br.com.uesb.ceasadigital.api.features.confirmation_token.service.ConfirmationTokenService;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserRegisterDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.response.UserResponseDTO;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para registro e login do usuario")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired 
    AuthService authService;

   // @Override
    @PostMapping( value= "/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDTO user){
        try { 
            UserResponseDTO userResponse = userService.create(user); 
            authService.sendConfirmationEmail(userService.findById(userResponse.getId()));
            return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirmToken")
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token){
        authService.confirmUserEmail(token);
        return ResponseEntity.ok(Map.of("message", "Email is confirmed sucessfully!"));
    }
  
}
