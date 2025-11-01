package br.com.uesb.ceasadigital.api.features.pedido.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para requisições de paginação de pedidos
 * Segue o mesmo padrão implementado no módulo de products
 */
public class PedidoPageRequestDto {

    @Schema(description = "Número da página (começando em 0)", example = "0", defaultValue = "0")
    @Min(value = 0, message = "O número da página deve ser maior ou igual a 0")
    private Integer page = 0;

    @Schema(description = "Tamanho da página", example = "10", defaultValue = "10")
    @Min(value = 1, message = "O tamanho da página deve ser maior que 0")
    private Integer size = 10;

    @Schema(description = "Direção da ordenação", example = "desc", defaultValue = "desc", 
            allowableValues = {"asc", "desc"})
    @Pattern(regexp = "^(asc|desc)$", message = "A direção deve ser 'asc' ou 'desc'")
    private String direction = "desc";

    @Schema(description = "Campo para ordenação baseada em tempo", example = "dataPedido", defaultValue = "dataPedido",
            allowableValues = {"dataPedido", "criadoEm", "atualizadoEm"})
    @Pattern(regexp = "^(dataPedido|criadoEm|atualizadoEm)$", 
             message = "O campo de ordenação deve ser 'dataPedido', 'criadoEm' ou 'atualizadoEm'")
    private String sortBy = "dataPedido";

    public PedidoPageRequestDto() {
    }

    public PedidoPageRequestDto(Integer page, Integer size, String direction, String sortBy) {
        this.page = page != null ? page : 0;
        this.size = size != null ? size : 10;
        this.direction = direction != null ? direction : "desc";
        this.sortBy = sortBy != null ? sortBy : "dataPedido";
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page != null ? page : 0;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size != null ? size : 10;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction != null ? direction : "desc";
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy != null ? sortBy : "dataPedido";
    }
}