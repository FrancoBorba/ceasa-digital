package br.com.uesb.ceasadigital.api.features.user.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.role.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
@Profile({"test", "dev"})
public class User implements UserDetails {
  
  /* Base fiels for User */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String name;
  
  @Column(unique = true)
  private String email;
  
  private String password;
  private String telefone;
  
  @Column(unique = true, nullable = false, length = 14)
  private String cpf;
  
  @UpdateTimestamp
  @Column(name = "atualizado_em")
  private LocalDateTime atualizadoEm;
  
  @ManyToMany
  @JoinTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
  
  /* Other Fields */
  
  @OneToMany(mappedBy = "usuario")
  private List<Pedido> pedidos = new ArrayList<>();
  
  /* Getters and Setters */
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getTelefone() {
    return telefone;
  }
  
  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }
  
  public String getCpf() {
    return cpf;
  }
  
  public void setCpf(String cpf) {
    this.cpf = cpf;
  }
  
  public LocalDateTime getAtualizadoEm() {
    return atualizadoEm;
  }
  
  public void setAtualizadoEm(LocalDateTime atualizadoEm) {
    this.atualizadoEm = atualizadoEm;
  }
  
  public List<Pedido> getPedidos() {
    return pedidos;
  }
  
  public Set<Role> getRoles() {
    return roles;
  }
  
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
  
  public void addRole(Role role) {
    this.roles.add(role);
  }
  
  public boolean hasRole(String roleName) {
    for (Role role : roles) {
      if (role.getAuthority().equals(roleName)) {
        return true;
      }
    }
    return false;
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }
  
  @Override
  public String getUsername() {
    return this.email;
  }
  
  @Override
  public boolean isAccountNonExpired() {
    // Considerando que a conta não está expirada por padrão
    return true;
  }
  
  @Override
  public boolean isAccountNonLocked() {
    // Considerando que o usuário não está bloqueado por padrão
    return true;
  }
  
  @Override
  public boolean isCredentialsNonExpired() {
    // Considerando que as credenciais não estão expiradas por padrão
    return true;
  }
  
  @Override
  public boolean isEnabled() {
    // Considerando que o usuário está habilitado por padrão
    return true;
  }
  
}
