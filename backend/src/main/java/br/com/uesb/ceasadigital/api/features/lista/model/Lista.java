package br.com.uesb.ceasadigital.api.features.lista.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.item_lista.model.ItemLista;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_listas_de_compras")
/**
 * Entidade que representa a lista de compras do usuário, alinhada com a
 * migration V09 (tb_listas_de_compras).
 *
 * Campos principais:
 * - id: chave primária
 * - usuario_id: referência ao usuário dono da lista
 * - nome: nome da lista (até 100 chars)
 * - descricao: descrição livre (TEXT)
 * - criado_em / atualizado_em: timestamps gerenciados pelo Hibernate
 */
public class Lista {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String nome;

  @Column(name = "descricao", columnDefinition = "TEXT")
  private String descricao;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private User usuario;

  @CreationTimestamp
  @Column(name = "criado_em", updatable = false)
  private LocalDateTime criadoEm;

  @UpdateTimestamp
  @Column(name = "atualizado_em")
  private LocalDateTime atualizadoEm;

  @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemLista> itens = new ArrayList<>();

  public Lista() {}

  public Lista(User usuario, String nome) {
    this.usuario = usuario;
    this.nome = nome;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public User getUsuario() {
    return usuario;
  }

  public void setUsuario(User usuario) {
    this.usuario = usuario;
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

  public List<ItemLista> getItens() {
    return itens;
  }

  public void setItens(List<ItemLista> itens) {
    this.itens = itens;
  }
}