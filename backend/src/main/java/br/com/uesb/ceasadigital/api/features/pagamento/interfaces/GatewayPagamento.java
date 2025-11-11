package br.com.uesb.ceasadigital.api.features.pagamento.interfaces;

import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.response.ResultadoCobrancaDTO;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;

public interface GatewayPagamento {
    
    ResultadoCobrancaDTO iniciarCobrancaPix(Pedido pedido);
}
