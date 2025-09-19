package br.com.uesb.ceasadigital.api.features.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${DATASOURCE_URL}")
    private String databaseUrl;

    @GetMapping("/public")
    public String debug() {
        return "Hello World Public";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String helloWorld() {
        return "Hello World Admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String helloWorldUser() {
        return "Hello World User";
    }

}
