package br.com.uesb.ceasadigital.api.features.pedido.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para respostas de paginação de pedidos
 * Segue o mesmo padrão implementado no módulo de products
 */
public class PedidoPageResponseDto {

    @Schema(description = "Lista de pedidos da página atual")
    private List<PedidoResponseDTO> content;

    @Schema(description = "Número da página atual", example = "0")
    private Integer page;

    @Schema(description = "Tamanho da página", example = "10")
    private Integer size;

    @Schema(description = "Número total de elementos", example = "100")
    private Long totalElements;

    @Schema(description = "Número total de páginas", example = "10")
    private Integer totalPages;

    @Schema(description = "Indica se é a primeira página", example = "true")
    private Boolean first;

    @Schema(description = "Indica se é a última página", example = "false")
    private Boolean last;

    @Schema(description = "Indica se há próxima página", example = "true")
    private Boolean hasNext;

    @Schema(description = "Indica se há página anterior", example = "false")
    private Boolean hasPrevious;

    public PedidoPageResponseDto() {
    }

    public PedidoPageResponseDto(Page<PedidoResponseDTO> page) {
        this.content = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
    }

    public List<PedidoResponseDTO> getContent() {
        return content;
    }

    public void setContent(List<PedidoResponseDTO> content) {
        this.content = content;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean getHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(Boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
}