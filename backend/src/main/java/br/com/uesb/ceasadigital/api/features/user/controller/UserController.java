package br.com.uesb.ceasadigital.api.features.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdatePasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdateRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.response.UserResponseDTO;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
  
  @Autowired
  private UserService userService;
  
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
  
  @PatchMapping("/me")
  @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
  public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserUpdateRequestDTO userUpdateDTO) {
    UserResponseDTO updatedUser = userService.updateUser(userUpdateDTO);
    return ResponseEntity.ok(updatedUser);
  }
  
  @PutMapping("/me/password")
  @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
  public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserUpdatePasswordRequestDTO passwordRequestDTO) {
    userService.updatePassword(passwordRequestDTO);
    return ResponseEntity.noContent().build();
  }
}
