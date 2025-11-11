package br.com.uesb.ceasadigital.api.features.pagamento.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.response.ResultadoCobrancaDTO;
import br.com.uesb.ceasadigital.api.features.pagamento.interfaces.GatewayPagamento;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.pedido.service.PedidoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@Profile({"dev", "test"})
public class MockPagamentoService implements GatewayPagamento {

    private static final Logger logger = LoggerFactory.getLogger(MockPagamentoService.class);

    @Autowired
    @Lazy
    PedidoService pedidoService;

    @Override
    public ResultadoCobrancaDTO iniciarCobrancaPix(Pedido pedido) {
        logger.info("--- [MOCK PAGAMENTO] --- Iniciando cobrança PIX FALSA para o Pedido ID: {}", pedido.getId());
        logger.info("--- [MOCK PAGAMENTO] --- Valor: {}", pedido.getValorTotal());

        //  Criar um DTO de resposta fake
        String fakeTxid = "FAKE_TXID_" + pedido.getId();
        String fakeQrCodePayload = "00020126360014br.gov.bcb.pix0114" + fakeTxid + "...(payload_fake)";
        String fakeImageUrl = "/api/mock/pix/qrcode/" + fakeTxid; // Um link fake para a imagem do QR Code

        ResultadoCobrancaDTO resultado = new ResultadoCobrancaDTO();
        

        resultado.setTxid(fakeTxid);
        resultado.setPixCopiaECola(fakeQrCodePayload); 
        resultado.setQrCodeImageUrl(fakeImageUrl);    

        // Simular a confirmação imediata do pagamento
        try {
      //      pedidoService.confirmarPagamento(pedido.getId());
            logger.info("--- [MOCK PAGAMENTO] --- Pagamento FAKE confirmado (instantaneamente) para o Pedido ID: {}", pedido.getId());
            resultado.setSucesso(true); // Define sucesso no DTO

        } catch (Exception e) {
            logger.error("--- [MOCK PAGAMENTO] --- Erro ao tentar confirmar pagamento FAKE para o Pedido ID: {}", pedido.getId(), e);
            resultado.setSucesso(false); // Define falha no DTO
        }

        return resultado;
    }
}
