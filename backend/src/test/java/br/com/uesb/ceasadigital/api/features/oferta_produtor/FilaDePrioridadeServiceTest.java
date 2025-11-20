package br.com.uesb.ceasadigital.api.features.oferta_produtor;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.enums.OfertaStatus;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.repository.OfertaProdutorRepository;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.service.FilaDePrioridadeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FilaDePrioridadeServiceTest {

    @Mock
    private OfertaProdutorRepository ofertaProdutorRepository;

    @InjectMocks
    private FilaDePrioridadeService filaDePrioridadeService;

    @Test
    void deveSelecionarPeloRodizio_QuandoVendedoresEstaoDentroDaMargem() {
        
        // --- 1. CENÁRIO (GIVEN) ---
        // Criação dos vendedores (Ofertas)
        
        // Vendedor A (Davi): Vendeu 10.0kg (menos volume), mas vendeu por último (data recente)
        OfertaProdutor davi = new OfertaProdutor();
        davi.setId(101L);
        davi.setTotalVolumeVendido(new BigDecimal("10.0"));
        davi.setDataUltimaVenda(LocalDateTime.now().minusHours(1)); // Vendeu 1h atrás
        
        // Vendedor B (João): Vendeu 10.5kg (dentro da margem de 10%), mas vendeu primeiro (data antiga)
        OfertaProdutor joao = new OfertaProdutor();
        joao.setId(102L);
        joao.setTotalVolumeVendido(new BigDecimal("10.5")); // 10.5 está dentro da margem de 10kg (limite 11kg)
        joao.setDataUltimaVenda(LocalDateTime.now().minusDays(1)); // Vendeu ontem

        // Vendedor C (Pedro): Vendeu 400kg (fora da margem)
        OfertaProdutor pedro = new OfertaProdutor();
        pedro.setId(103L);
        pedro.setTotalVolumeVendido(new BigDecimal("400.0"));
        pedro.setDataUltimaVenda(LocalDateTime.now().minusMinutes(30));

        // Define o produtoId e a quantidade desejada
        Long produtoId = 1L;
        BigDecimal quantidadeDesejada = new BigDecimal("2.0");

        // Configura o Mock do Repositório:
        // Quando o repositório for chamado com a ordenação por Volume...
        when(ofertaProdutorRepository.findByMetaEstoqueProdutoIdAndQuantidadeDisponivelGreaterThanEqualAndStatus(
                eq(produtoId), 
                eq(quantidadeDesejada), 
                eq(OfertaStatus.ATIVA), 
                any(Sort.class) // ...ele deve retornar esta lista ordenada por volume
        )).thenReturn(Arrays.asList(davi, joao, pedro));


        // --- 2. AÇÃO (WHEN) ---
        
        // Executamos o serviço
        OfertaProdutor vencedor = filaDePrioridadeService.selecionarOfertaDisponivel(produtoId, quantidadeDesejada);

        
        // --- 3. VERIFICAÇÃO (THEN) ---
        
        // O vencedor deve ser o JOÃO (ID 102), não o Davi.
        // Por quê?
        // 1. O 'davi' (10kg) foi o primeiro, e a margem (10%) foi calculada (limite 11kg).
        // 2. O 'joao' (10.5kg) entrou no "grupo de finalistas" por estar dentro da margem.
        // 3. O 'pedro' (400kg) foi descartado.
        // 4. Entre 'davi' e 'joao', o 'joao' ganhou no desempate por data (rodízio), pois vendeu 1 dia atrás.
        
        assertNotNull(vencedor);
        assertEquals(102L, vencedor.getId());
        assertEquals(joao, vencedor);
    }
}