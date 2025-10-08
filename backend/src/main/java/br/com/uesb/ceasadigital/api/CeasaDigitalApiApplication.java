package br.com.uesb.ceasadigital.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "CEASA Digital API", version = "v1", description = "API para a plataforma CEASA Digital"),
    security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
    name = "bearerAuth",
    scheme = "bearer",
    bearerFormat = "JWT",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER
)
public class CeasaDigitalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CeasaDigitalApiApplication.class, args);
	}

}
