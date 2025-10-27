package br.com.uesb.ceasadigital.api.features.produtor_produto.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta do processamento em lote da solicitação de venda.")
public class ProdutorProdutoResponseVendaDTO {
    @Schema(description = "Número total de solicitações recebidas.", example = "3")
    private int totalSolicitado;
    
    @Schema(description = "Lista detalhada das permissões criadas/atualizadas com o status 'PENDENTE'.")
    private List<ProdutorProdutoResponseDTO> resultados;

    public ProdutorProdutoResponseVendaDTO(List<ProdutorProdutoResponseDTO> resultados) {
        this.resultados = resultados;
        this.totalSolicitado = resultados.size();
    }

    /* getters e setters */
    public int getTotalSolicitado() {
        return totalSolicitado;
    }

    public void setTotalSolicitado(int totalSolicitado) {
        this.totalSolicitado = totalSolicitado;
    }

    public List<ProdutorProdutoResponseDTO> getResultados() {
        return resultados;
    }

    public void setResultados(List<ProdutorProdutoResponseDTO> resultados) {
        this.resultados = resultados;
    }
}
