package br.com.uesb.ceasadigital.api.features.oferta_produtor.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.enums.OfertaStatus;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.repository.OfertaProdutorRepository;

@Service
public class FilaDePrioridadeService {
    
    @Autowired
    OfertaProdutorRepository ofertaProdutorRepository;

    // Define a margem de tolerância. 1.10 = 10%
    // (Podemos colocar isso em um application.properties no futuro)
    private static final BigDecimal MARGEM_TOLERANCIA = new BigDecimal("1.10");

    public OfertaProdutor selecionarOfertaDisponivel(Long produtoId, BigDecimal quantidadeDesejada) {
        
        // --- ETAPA 1: FILTRAGEM (BANCO DE DADOS) ---
        // 1. A prioridade principal (volume) é usada para a busca inicial no banco.
        Sort prioridadeVolume = Sort.by(Sort.Direction.ASC, "totalVolumeVendido");

        // 2. Busca todos os candidatos elegíveis, já ordenados por volume.
        List<OfertaProdutor> candidatosPorVolume = ofertaProdutorRepository
            .findByMetaEstoqueProdutoIdAndQuantidadeDisponivelGreaterThanEqualAndStatus(
                produtoId,
                quantidadeDesejada,
                OfertaStatus.ATIVA,
                prioridadeVolume
            );

        if (candidatosPorVolume.isEmpty()) {
            throw new ResourceNotFoundException("Não há ofertas ou estoque disponível para a quantidade solicitada deste produto.");
        }

        // ---APLICAÇÃO DA MARGEM  ---
        
        //  Pega o primeiro da fila (quem vendeu menos) e calcula a margem.
        OfertaProdutor vencedorPorVolume = candidatosPorVolume.get(0);
        BigDecimal menorVolume = vencedorPorVolume.getTotalVolumeVendido();
        BigDecimal limiteDaMargem = menorVolume.multiply(MARGEM_TOLERANCIA);

        //  Busca pela margem
        List<OfertaProdutor> finalistas = candidatosPorVolume.stream()
            .filter(candidato -> candidato.getTotalVolumeVendido().compareTo(limiteDaMargem) <= 0)
            .collect(Collectors.toList());


        // Compara por 'dataUltimaVenda', tratando nulos (quem nunca vendeu) como prioridade.
        Comparator<OfertaProdutor> comparadorPorData = Comparator.comparing(
            OfertaProdutor::getDataUltimaVenda, 
            Comparator.nullsFirst(Comparator.naturalOrder())
        );

        OfertaProdutor vencedorFinal = finalistas.stream()
            .min(comparadorPorData)
            .orElse(vencedorPorVolume); 

        return vencedorFinal;
    }
}
