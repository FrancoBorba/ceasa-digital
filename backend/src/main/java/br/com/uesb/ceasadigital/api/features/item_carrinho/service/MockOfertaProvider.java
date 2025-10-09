package br.com.uesb.ceasadigital.api.features.item_carrinho.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Profile("dev") // Só ativa em ambiente de desenvolvimento
public class MockOfertaProvider {

    // ====================================================================
    // 1. DEFINIÇÃO DOS CONTRATOS (DTOs) - Leves e sem JPA
    // Estas classes só existem aqui dentro, sem risco de conflito!
    // ====================================================================

    public static class EstoqueVirtualDTO {
        private Long id;
        private Long produtoId;
        private BigDecimal precoDefinido;
        // Adicione outros campos se precisar...

        // Getters e Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getProdutoId() { return produtoId; }
        public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
        public BigDecimal getPrecoDefinido() { return precoDefinido; }
        public void setPrecoDefinido(BigDecimal preco) { this.precoDefinido = preco; }
    }

    public static class OfertaProdutorDTO {
        private Long id;
        private Long produtorId;
        private BigDecimal quantidadeDisponivel;
        private boolean ativa;
        private EstoqueVirtualDTO estoqueVirtual; // Aninhado aqui!

        // Getters e Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getProdutorId() { return produtorId; }
        public void setProdutorId(Long produtorId) { this.produtorId = produtorId; }
        public BigDecimal getQuantidadeDisponivel() { return quantidadeDisponivel; }
        public void setQuantidadeDisponivel(BigDecimal qtd) { this.quantidadeDisponivel = qtd; }
        public boolean isAtiva() { return ativa; }
        public void setAtiva(boolean ativa) { this.ativa = ativa; }
        public EstoqueVirtualDTO getEstoqueVirtual() { return estoqueVirtual; }
        public void setEstoqueVirtual(EstoqueVirtualDTO estoque) { this.estoqueVirtual = estoque; }
    }

    // ====================================================================
    // 2. BANCO DE DADOS FAKE E LÓGICA DO MOCK
    // ====================================================================

    private final Map<Long, OfertaProdutorDTO> MOCK_DB = new HashMap<>();

    @PostConstruct
    private void init() {
        // Cenário: Batatas, com preço definido no estoque
        EstoqueVirtualDTO estoqueBatata = new EstoqueVirtualDTO();
        estoqueBatata.setId(10L);
        estoqueBatata.setProdutoId(1L);
        estoqueBatata.setPrecoDefinido(new BigDecimal("4.50"));

        // Oferta do Produtor A para batatas
        OfertaProdutorDTO ofertaA = new OfertaProdutorDTO();
        ofertaA.setId(101L);
        ofertaA.setProdutorId(201L);
        ofertaA.setQuantidadeDisponivel(new BigDecimal("150.0"));
        ofertaA.setAtiva(true);
        ofertaA.setEstoqueVirtual(estoqueBatata); // Associa ao estoque

        // Oferta do Produtor B para batatas
        OfertaProdutorDTO ofertaB = new OfertaProdutorDTO();
        ofertaB.setId(102L);
        ofertaB.setProdutorId(202L);
        ofertaB.setQuantidadeDisponivel(new BigDecimal("300.0"));
        ofertaB.setAtiva(true);
        ofertaB.setEstoqueVirtual(estoqueBatata);

        MOCK_DB.put(ofertaA.getId(), ofertaA);
        MOCK_DB.put(ofertaB.getId(), ofertaB);
        
        System.out.println("--- MODO DEV: MockOfertaProvider INICIADO ---");
    }

    /**
     * O único método que seu serviço vai chamar.
     * Busca a oferta completa com as informações de estoque aninhadas.
     */
    public Optional<OfertaProdutorDTO> buscarOfertaCompleta(Long ofertaId) {
         System.out.println("--- MODO DEV: Buscando DTO da oferta mockada com ID: " + ofertaId + " ---");
        return Optional.ofNullable(MOCK_DB.get(ofertaId));
    }
}