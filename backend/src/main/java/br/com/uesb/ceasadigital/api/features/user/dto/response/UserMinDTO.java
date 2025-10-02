package br.com.uesb.ceasadigital.api.features.user.dto.response;

import br.com.uesb.ceasadigital.api.features.user.model.User;

public class UserMinDTO {

  private Long id;
  private String name;
  private String email;

  public UserMinDTO() {
  }

  public UserMinDTO(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
