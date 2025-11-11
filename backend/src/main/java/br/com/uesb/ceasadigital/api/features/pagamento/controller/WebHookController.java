package br.com.uesb.ceasadigital.api.features.pagamento.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.webhook.SicoobWebhookPayloadDTO;
import br.com.uesb.ceasadigital.api.features.pedido.service.PedidoService;

@RestController
@RequestMapping("/api/webhooks")
public class WebHookController {
    
    private static final Logger logger = LoggerFactory.getLogger(WebHookController.class);
    
    @Autowired
    PedidoService pedidoService;

    @PostMapping("/sicoob-pix")
    public ResponseEntity<Void> receberNotificacaoSicoob(
        @RequestBody SicoobWebhookPayloadDTO payloadDTO
    ){

        // TODO: Adicionar validação de segurança 

        logger.info("Notificação de Webhook PIX recebida do Sicoob.");

        try{
            if(payloadDTO != null && payloadDTO.getPix() != null && !payloadDTO.getPix().isEmpty()){


                for(var notificao :payloadDTO.getPix()){
                    pedidoService.processarNotificacaoWebHook(notificao);
                }
            }else{
              
                logger.warn("Payload do webhook PIX Sicoob veio vazio ou mal formatado.");
            }
            return ResponseEntity.ok().build();
            }
            
        catch (Exception e) {
            logger.error("Erro ao processar webhook Sicoob PIX: {}", e.getMessage(), e);
            // Retorna um erro 500. O Sicoob pode tentar enviar novamente.
            return ResponseEntity.internalServerError().build();
        }
    }


}



