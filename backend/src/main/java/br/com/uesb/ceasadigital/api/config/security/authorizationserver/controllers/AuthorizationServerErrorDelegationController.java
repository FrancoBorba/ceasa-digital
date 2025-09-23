package br.com.uesb.ceasadigital.api.config.security.authorizationserver.controllers;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Hidden // Hide this controller from Swagger documentation
public class AuthorizationServerErrorDelegationController {

    @RequestMapping("/error/oauth2")
    public void handleOAuth2Error(HttpServletRequest request) throws OAuth2AuthenticationException {
        OAuth2AuthenticationException oauth2Exception = 
            (OAuth2AuthenticationException) request.getAttribute("oauth2.error.exception");
        
        if (oauth2Exception != null) {
            throw oauth2Exception;
        }
        
        OAuth2Error genericError = new OAuth2Error("oauth2_error", "OAuth2 authentication error", null);
        throw new OAuth2AuthenticationException(genericError);
    }
}
