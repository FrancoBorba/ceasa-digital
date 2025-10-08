package br.com.uesb.ceasadigital.api.config.security.authorizationserver.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/oauth2-docs")
@Tag(name = "OAuth2 Authentication", description = "Endpoints de autenticação OAuth2 - Use estes endpoints no Swagger")
public class OAuth2DocumentationController {

    private final RestTemplate restTemplate;

    @Value("${security.client-id}")
    private String clientId;

    @Value("${security.client-secret}")
    private String clientSecret;

    @Value("${server.port:8080}")
    private String serverPort;

    public OAuth2DocumentationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SecurityRequirements
    @Operation(
        summary = "Login - Autenticação com username e password",
        description = """
            Endpoint para obter tokens de acesso usando credenciais de usuário.
            
            **Como usar no Swagger:**
            1. Clique em "Try it out"
            2. Preencha o username (email) e password nos campos individuais
            3. Clique em "Execute"
            
            Este endpoint faz um proxy para o endpoint real do Spring Security OAuth2.
            Copie o access_token da resposta e use o botão "Authorize" no topo da página para autenticar nas demais requisições.
            """,
        tags = {"OAuth2 Authentication"},
        responses = {
            @ApiResponse(
                description = "Autenticação bem-sucedida",
                responseCode = "200",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
                )
            ),
            @ApiResponse(
                description = "Credenciais inválidas",
                responseCode = "401",
                content = @Content
            ),
            @ApiResponse(
                description = "Bad Request - Parâmetros inválidos",
                responseCode = "400",
                content = @Content
            )
        }
    )
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> login(
            @Parameter(description = "Email do usuário", required = true, example = "maria@gmail.com")
            @RequestParam String username,
            
            @Parameter(description = "Senha do usuário", required = true, example = "123456")
            @RequestParam String password,
            
            @Parameter(
                description = "Tipo de concessão OAuth2",
                required = true,
                schema = @Schema(defaultValue = "password", allowableValues = {"password"})
            )
            @RequestParam(defaultValue = "password") String grant_type) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            String auth = clientId + ":" + clientSecret;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            headers.set("Authorization", "Basic " + encodedAuth);
            
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("username", username);
            body.add("password", password);
            body.add("grant_type", grant_type);
            
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
            
            String url = "http://localhost:" + serverPort + "/oauth2/token";
            
            @SuppressWarnings("unchecked")
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                (Class<Map<String, Object>>)(Class<?>) Map.class
            );
            
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "authentication_failed",
                "error_description", e.getMessage()
            ));
        }
    }

    @SecurityRequirements
    @Operation(
        summary = "Refresh Token - Renovação de token",
        description = """
            Endpoint para renovar o access_token usando um refresh_token válido.
            
            **Como usar no Swagger:**
            1. Clique em "Try it out"
            2. Cole o refresh_token obtido no login no campo
            3. Clique em "Execute"
            
            Este endpoint faz um proxy para o endpoint real do Spring Security OAuth2.
            """,
        tags = {"OAuth2 Authentication"},
        responses = {
            @ApiResponse(
                description = "Token renovado com sucesso",
                responseCode = "200",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
                )
            ),
            @ApiResponse(
                description = "Refresh token inválido ou expirado",
                responseCode = "401",
                content = @Content
            ),
            @ApiResponse(
                description = "Bad Request - Parâmetros inválidos",
                responseCode = "400",
                content = @Content
            )
        }
    )
    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> refreshToken(
            @Parameter(description = "Refresh token obtido no login", required = true)
            @RequestParam String refresh_token,
            
            @Parameter(
                description = "Tipo de concessão OAuth2",
                required = true,
                schema = @Schema(defaultValue = "refresh_token", allowableValues = {"refresh_token"})
            )
            @RequestParam(defaultValue = "refresh_token") String grant_type) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            String auth = clientId + ":" + clientSecret;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            headers.set("Authorization", "Basic " + encodedAuth);
            
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("refresh_token", refresh_token);
            body.add("grant_type", grant_type);
            
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
            
            String url = "http://localhost:" + serverPort + "/oauth2/token";
            
            @SuppressWarnings("unchecked")
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                (Class<Map<String, Object>>)(Class<?>) Map.class
            );
            
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "token_refresh_failed",
                "error_description", e.getMessage()
            ));
        }
    }
}

