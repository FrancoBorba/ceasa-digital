package br.com.uesb.ceasadigital.api.features.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  
  @GetMapping()
  public String helloWorld() {
    return "Hello World";
  }
}
