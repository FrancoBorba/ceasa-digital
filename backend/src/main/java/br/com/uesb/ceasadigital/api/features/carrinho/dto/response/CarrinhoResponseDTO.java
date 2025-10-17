package br.com.uesb.ceasadigital.api.features.carrinho.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.response.CarrinhoItemResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

public class CarrinhoResponseDTO {

    @Schema(description = "ID único do carrinho", example = "1")
    private Long id;

    @Schema(description = "ID do usuário proprietário do carrinho", example = "1")
    private Long usuarioId;

    @Schema(description = "Nome do usuário proprietário do carrinho", example = "João Silva")
    private String usuarioNome;

    @Schema(description = "Data e hora de criação do carrinho", example = "2024-01-15T10:30:00")
    private LocalDateTime criadoEm;

    @Schema(description = "Data e hora da última atualização do carrinho", example = "2024-01-15T14:45:00")
    private LocalDateTime atualizadoEm;

    private List<CarrinhoItemResponseDTO> itens;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
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
    public List<CarrinhoItemResponseDTO> getItens() {
      return itens;
    }

    public void setItens(List<CarrinhoItemResponseDTO> itens) {
      this.itens = itens;
    }

    public BigDecimal getTotal() {
      BigDecimal totalSum = BigDecimal.ZERO;
      for (CarrinhoItemResponseDTO item : itens) {
        totalSum = totalSum.add(item.getSubTotal());
      }
      return totalSum;
    }
}