package br.com.uesb.ceasadigital.api.config.security.resourceserver.controllers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@Hidden // Hide this controller from Swagger documentation
public class ResourceServerErrorDelegationController {

    @RequestMapping("/error/auth")
    public void handleAuthenticationError(HttpServletRequest request) throws AuthenticationException {
        AuthenticationException exception = (AuthenticationException) request.getAttribute("javax.servlet.error.exception");
        if (exception != null) {
            throw exception;
        }
        throw new AuthenticationException("Missing or invalid access token") {};
    }
}
