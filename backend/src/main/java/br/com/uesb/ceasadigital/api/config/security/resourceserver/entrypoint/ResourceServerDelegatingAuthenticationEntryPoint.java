package br.com.uesb.ceasadigital.api.config.security.resourceserver.entrypoint;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ResourceServerDelegatingAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        request.setAttribute("javax.servlet.error.exception", authException);
        request.getRequestDispatcher("/error/auth").forward(request, response);
    }
}
