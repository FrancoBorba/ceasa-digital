package br.com.uesb.ceasadigital.api.features.oferta_produtor.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.repository.OfertaProdutorRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class EstoqueService {

    private final OfertaProdutorRepository ofertaRepository;

    public EstoqueService(OfertaProdutorRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    /**
     * Reserva produtos de uma oferta (pre-compra)
     */
    @Transactional
    public boolean reservarProdutos(Long ofertaId, BigDecimal quantidade) {
        OfertaProdutor oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new RuntimeException("Oferta não encontrada"));

        if (oferta.getQuantidadeDisponivel().compareTo(quantidade) < 0) {
            return false; // sem estoque suficiente
        }

        oferta.setQuantidadeDisponivel(oferta.getQuantidadeDisponivel().subtract(quantidade));
        ofertaRepository.save(oferta);
        return true;
    }

    /**
     * Confirma uma compra — consolida o volume vendido
     */
    @Transactional
    public void confirmarCompra(Long ofertaId, BigDecimal quantidade) {
        OfertaProdutor oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new RuntimeException("Oferta não encontrada"));

        oferta.setTotalVolumeVendido(oferta.getTotalVolumeVendido().add(quantidade));
        oferta.setDataUltimaVenda(LocalDateTime.now());
        ofertaRepository.save(oferta);
    }

    /**
     * Cancela uma reserva, devolvendo a quantidade ao estoque disponível
     */
    @Transactional
    public void cancelarCompra(Long ofertaId, BigDecimal quantidade) {
        OfertaProdutor oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new RuntimeException("Oferta não encontrada"));

        oferta.setQuantidadeDisponivel(oferta.getQuantidadeDisponivel().add(quantidade));
        ofertaRepository.save(oferta);
    }
}
