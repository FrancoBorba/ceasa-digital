package br.com.uesb.ceasadigital.api.features.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${DEV_DATASOURCE_URL}")
    private String databaseUrl;

    @GetMapping()
    public String helloWorld() {
        return databaseUrl;
    }

}
