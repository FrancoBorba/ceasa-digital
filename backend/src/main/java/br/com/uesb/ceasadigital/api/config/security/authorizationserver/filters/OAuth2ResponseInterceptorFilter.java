package br.com.uesb.ceasadigital.api.config.security.authorizationserver.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.uesb.ceasadigital.api.config.security.authorizationserver.wrappers.OAuth2ResponseCapturingWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
public class OAuth2ResponseInterceptorFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${security.jwt.duration:3600}")
    private Integer accessTokenDurationSeconds;
    
    @Value("${security.jwt.refresh-duration:7200}")
    private Integer refreshTokenDurationSeconds;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                   FilterChain filterChain) throws ServletException, IOException {

        // Check if it's a request for the OAuth2 token endpoint
        if (!"/oauth2/token".equals(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        OAuth2ResponseCapturingWrapper responseWrapper = new OAuth2ResponseCapturingWrapper(response);
        filterChain.doFilter(request, responseWrapper);

        // Check if there was an error (status >= 400)
        if (responseWrapper.getStatus() >= 400) {
            String originalContent = responseWrapper.getCapturedContent();
            
            try {
                // Try to parse the JSON error OAuth2
                JsonNode errorNode = objectMapper.readTree(originalContent);
                
                if (errorNode.has("error")) {
                    String error = errorNode.get("error").asText();
                    String errorDescription = errorNode.has("error_description") ? 
                        errorNode.get("error_description").asText() : null;

                    // Create OAuth2 exception to delegate to ControllerExceptionHandler
                    OAuth2Error oauth2Error = new OAuth2Error(error, errorDescription, null);
                    OAuth2AuthenticationException oauth2Exception = new OAuth2AuthenticationException(oauth2Error);
                    
                    // Set attributes for the ControllerExceptionHandler
                    request.setAttribute("oauth2.error.exception", oauth2Exception);
                    request.setAttribute("oauth2.error.status", responseWrapper.getStatus());
                    
                    // Redirect to endpoint that will trigger the @ExceptionHandler
                    request.getRequestDispatcher("/error/oauth2").forward(request, response);
                    return;
                }
            } catch (Exception e) {
                // If unable to parse, create generic exception
                OAuth2Error oauth2Error = new OAuth2Error("oauth2_error", "OAuth2 authentication error", null);
                OAuth2AuthenticationException oauth2Exception = new OAuth2AuthenticationException(oauth2Error);
                
                request.setAttribute("oauth2.error.exception", oauth2Exception);
                request.setAttribute("oauth2.error.status", responseWrapper.getStatus());
                
                request.getRequestDispatcher("/error/oauth2").forward(request, response);
                return;
            }
        }

        // Check if it's a successful token response (status 200)
        if (responseWrapper.getStatus() == 200) {
            String originalContent = responseWrapper.getCapturedContent();
            
            try {
                // Parse the successful token response
                JsonNode tokenResponse = objectMapper.readTree(originalContent);
                
                // Check if it has access_token (meaning it's a token response)
                if (tokenResponse.has("access_token")) {
                    // Create a mutable copy of the JSON
                    ObjectNode enhancedResponse = (ObjectNode) tokenResponse;
                    
                    // Replace expires_in with access_token_expires_in
                    if (tokenResponse.has("expires_in")) {
                        enhancedResponse.remove("expires_in");
                        enhancedResponse.put("access_token_expires_in", accessTokenDurationSeconds);
                    }
                    
                    // Add refresh token expires_in if refresh_token exists
                    if (tokenResponse.has("refresh_token")) {
                        enhancedResponse.put("refresh_token_expires_in", refreshTokenDurationSeconds);
                    }
                    
                    // Write the enhanced response
                    String enhancedContent = objectMapper.writeValueAsString(enhancedResponse);
                    response.getOutputStream().write(enhancedContent.getBytes());
                    return;
                }
            } catch (Exception e) {
                // If parsing fails, fall back to original response
            }
        }

        // If it reached here, write the original response (success or non-OAuth2 error)
        response.getOutputStream().write(responseWrapper.getCapturedBytes());
    }

}
