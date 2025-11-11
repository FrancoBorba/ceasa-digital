package br.com.uesb.ceasadigital.api.features.endereco.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.uesb.ceasadigital.api.features.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_enderecos")
public class Endereco {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private User usuario;

  @Column(nullable = false, length = 9)
  private String cep;

  @Column(nullable = false, length = 255)
  private String logradouro;

  @Column(nullable = false, length = 20)
  private String numero;

  @Column(length = 100)
  private String complemento;

  @Column(nullable = false, length = 100)
  private String bairro;

  @Column(nullable = false, length = 100)
  private String cidade;

  @Column(nullable = false, length = 2)
  private String estado;

  @Column(nullable = false)
  private Boolean principal = false;

  @CreationTimestamp
  @Column(name = "criado_em", updatable = false)
  private LocalDateTime criadoEm;

  @UpdateTimestamp
  @Column(name = "atualizado_em")
  private LocalDateTime atualizadoEm;

  @Column(name = "nome_endereco", length = 50)
  private String nomeEndereco;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUsuario() {
    return usuario;
  }

  public void setUsuario(User usuario) {
    this.usuario = usuario;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getLogradouro() {
    return logradouro;
  }

  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getComplemento() {
    return complemento;
  }

  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }

  public String getBairro() {
    return bairro;
  }

  public void setBairro(String bairro) {
    this.bairro = bairro;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Boolean getPrincipal() {
    return principal;
  }

  public void setPrincipal(Boolean principal) {
    this.principal = principal;
  }

  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }

  public void setCriadoEm(LocalDateTime criadoEm) {
    this.criadoEm = criadoEm;
  }

  public LocalDateTime getAtualizadoEm() {
    return atualizadoEm;
  }

  public void setAtualizadoEm(LocalDateTime atualizadoEm) {
    this.atualizadoEm = atualizadoEm;
  }

  public String getNomeEndereco() {
    return nomeEndereco;
  }

  public void setNomeEndereco(String nomeEndereco) {
    this.nomeEndereco = nomeEndereco;
  }
}
