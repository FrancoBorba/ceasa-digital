package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// Ignora campos desconhecidos
@JsonIgnoreProperties(ignoreUnknown = true)
public class SicoobWebhookPayloadDTO {

    private List<SicoobPixNotificacaoDTO> pix; // A lista de notificações

    // Getters e Setters
    public List<SicoobPixNotificacaoDTO> getPix() {
        return pix;
    }
    public void setPix(List<SicoobPixNotificacaoDTO> pix) {
        this.pix = pix;
    }
}