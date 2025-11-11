package br.com.uesb.ceasadigital.api.features.lista.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import br.com.uesb.ceasadigital.api.features.item_lista.dto.response.ItemListaResponseDTO;

public class ListaResponseDTO {

  @Schema(description = "ID da lista", example = "1")
  private Long id;

  @Schema(description = "Nome da lista", example = "Compras da semana")
  private String nome;

  @Schema(description = "Descrição da lista", example = "Itens para o churrasco de sábado")
  private String descricao;

  @Schema(description = "ID do usuário dono da lista", example = "5")
  private Long usuarioId;

  @Schema(description = "Nome do usuário dono da lista", example = "João Silva")
  private String usuarioNome;

  @Schema(description = "Data de criação", example = "2024-01-15T10:30:00")
  private LocalDateTime criadoEm;

  @Schema(description = "Data de atualização", example = "2024-01-15T14:45:00")
  private LocalDateTime atualizadoEm;

  private List<ItemListaResponseDTO> itens;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }

  public String getDescricao() { return descricao; }
  public void setDescricao(String descricao) { this.descricao = descricao; }

  public Long getUsuarioId() { return usuarioId; }
  public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

  public String getUsuarioNome() { return usuarioNome; }
  public void setUsuarioNome(String usuarioNome) { this.usuarioNome = usuarioNome; }

  public LocalDateTime getCriadoEm() { return criadoEm; }
  public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

  public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
  public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }

  public List<ItemListaResponseDTO> getItens() { return itens; }
  public void setItens(List<ItemListaResponseDTO> itens) { this.itens = itens; }
}