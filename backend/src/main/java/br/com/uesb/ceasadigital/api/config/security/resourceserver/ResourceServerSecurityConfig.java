package br.com.uesb.ceasadigital.api.config.security.resourceserver;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import br.com.uesb.ceasadigital.api.config.security.resourceserver.entrypoint.ResourceServerDelegatingAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ResourceServerSecurityConfig {

  private final ResourceServerDelegatingAuthenticationEntryPoint delegatingAuthenticationEntryPoint;

  @Value("${cors.origins}")
	private String corsOrigins;

	@Value("${security.oauth2.jwk-set-uri}")
	private String jwkSetUri;

	@Value("${security.oauth2.issuer-uri}")
	private String issuerUri;

	public ResourceServerSecurityConfig(ResourceServerDelegatingAuthenticationEntryPoint delegatingAuthenticationEntryPoint) {
    this.delegatingAuthenticationEntryPoint = delegatingAuthenticationEntryPoint;
  }

	@Bean
	@Profile("test")
	@Order(1)
	public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {

		http.securityMatcher(PathRequest.toH2Console()).csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
		return http.build();
	}

	@Bean
	@Order(3)
	public SecurityFilterChain rsSecurityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(authorize -> authorize
				// Add public endpoints here:
				.requestMatchers("/users/public").permitAll()
				
				// Swagger UI and OpenAPI endpoints (public access)
				.requestMatchers(
					"/swagger-ui/**",      // Interface of Swagger UI
					"/v3/api-docs/**",     // Configuration of the API (JSON)
					"/swagger-resources/**", // Resources of Swagger
					"/webjars/**"          // libraries JS/CSS
				).permitAll()

        // Docker Health Check Endpoints
        .requestMatchers("/actuator/**").permitAll()

				// Internal endpoints for error delegation (protected from external access)
				.requestMatchers("/error/auth").access((authentication, context) -> {
					// Only allow internal forwards (when attributes are set by our components)
					HttpServletRequest request = context.getRequest();
					return new org.springframework.security.authorization.AuthorizationDecision(
						request.getAttribute("javax.servlet.error.exception") != null ||
						request.getAttribute("oauth2.error.exception") != null
					);
				})
				.requestMatchers("/error/oauth2").access((authentication, context) -> {
					// Only allow internal forwards (when attributes are set by our components)
					HttpServletRequest request = context.getRequest();
					return new org.springframework.security.authorization.AuthorizationDecision(
						request.getAttribute("oauth2.error.exception") != null
					);
				})

				.anyRequest().authenticated());
		http.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
		    .jwt(jwt -> jwt
		        .decoder(resourceServerJwtDecoder())
		        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
		    .authenticationEntryPoint(delegatingAuthenticationEntryPoint));
    http.exceptionHandling(exceptionHandling -> exceptionHandling
      .authenticationEntryPoint(delegatingAuthenticationEntryPoint));
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
		return http.build();
	}

	@Bean
	@Primary
	public JwtDecoder resourceServerJwtDecoder() {
    // Configure the JwtDecoder using the environment variables
		NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

		// Configure validators with very low clock tolerance
		OAuth2TokenValidator<Jwt> timestampValidator = new JwtTimestampValidator(Duration.ofSeconds(0)); // Zero clock skew
		OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
		OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(timestampValidator, withIssuer);

		jwtDecoder.setJwtValidator(validator);
		return jwtDecoder;
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
		grantedAuthoritiesConverter.setAuthorityPrefix("");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		String[] origins = corsOrigins.split(",");

		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOriginPatterns(Arrays.asList(origins));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
