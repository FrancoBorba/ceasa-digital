package br.com.uesb.ceasadigital.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "CEASA Digital API", version = "v1", description = "API para a plataforma CEASA Digital"),
    security = @SecurityRequirement(name = "bearerAuth")
)
@SecuritySchemes({
    @SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        description = "Cole aqui o access_token obtido através do endpoint /oauth2-docs/login"
    ),
    @SecurityScheme(
        name = "refreshToken",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-Refresh-Token",
        description = "Cole aqui o refresh_token obtido no login (usado para renovar o access_token)"
    )
})
public class CeasaDigitalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CeasaDigitalApiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
