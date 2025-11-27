package br.com.uesb.ceasadigital.api.features.produtor_produto.dto.response;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta para a permissão de venda de um produto por um produtor.")
public class ProdutorProdutoResponseDTO {
    private Long id;
    
    @Schema(description = "ID do produtor que solicitou a venda.")
    private Long produtorId;
    
    @Schema(description = "Nome do produtor que solicitou a venda.")
    private String produtorNome;
    
    @Schema(description = "ID do produto solicitado para venda.")
    private Long produtoId;
    
    @Schema(description = "Nome do produto solicitado para venda.")
    private String produtoNome;
    
    @Schema(description = "Status da permissão: ATIVO, PENDENTE, REJEITADO, INATIVO", example = "PENDENTE")
    private String status;
    
    @Schema(description = "URL da imagem do produto.")
    private String produtoImgUrl;
    
    @Schema(description = "Nome da categoria do produto.")
    private String produtoCategoriaNome;
    
    private LocalDateTime criadoEm;
    
    private LocalDateTime atualizadoEm;
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProdutorId() {
        return produtorId;
    }
    
    public void setProdutorId(Long produtorId) {
        this.produtorId = produtorId;
    }
    
    public String getProdutorNome() {
        return produtorNome;
    }
    
    public void setProdutorNome(String produtorNome) {
        this.produtorNome = produtorNome;
    }
    
    public Long getProdutoId() {
        return produtoId;
    }
    
    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }
    
    public String getProdutoNome() {
        return produtoNome;
    }
    
    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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
    
    public String getProdutoImgUrl() {
        return produtoImgUrl;
    }
    
    public void setProdutoImgUrl(String produtoImgUrl) {
        this.produtoImgUrl = produtoImgUrl;
    }
    
    public String getProdutoCategoriaNome() {
        return produtoCategoriaNome;
    }
    
    public void setProdutoCategoriaNome(String produtoCategoriaNome) {
        this.produtoCategoriaNome = produtoCategoriaNome;
    }
}