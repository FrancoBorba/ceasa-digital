package br.com.uesb.ceasadigital.api.common.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.user.model.User;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    // Pega o e-mail remetente da configuração (application.yml ou .env)
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Async // Para enviar e-mail de forma assíncrona (não bloquear a thread principal)
    public void sendSimpleMail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (MailException e) {
            throw new IllegalStateException("Failed to send email to " + to);

        }
    }

    @Async
    public void sendConfirmationEmail(User user, String confirmationToken, String confirmationUrlBase) {
        if (user == null || user.getEmail() == null) {
            throw new IllegalStateException("Tentativa de enviar e-mail de confirmação para usuário nulo ou sem e-mail.");
        }

        String recipientAddress = user.getEmail();
        String subject = "CEASA Digital - Confirme seu E-mail";
        String confirmationUrl = confirmationUrlBase + confirmationToken;
        String message = "Olá " + user.getName() + ",\n\n"
                       + "Obrigado por se registrar no CEASA Digital!\n\n"
                       + "Por favor, clique no link abaixo para confirmar seu endereço de e-mail:\n"
                       + confirmationUrl + "\n\n"
                       + "Se você não se registrou, por favor ignore este e-mail.\n\n"
                       + "Atenciosamente,\nEquipe CEASA Digital";

        sendSimpleMail(recipientAddress, subject, message);
    }

    @Async
    public void sendSolicitacaoVendaStatusEmail(User produtor, Product produto, String novoStatus) {
        if (produtor == null || produtor.getEmail() == null || produto == null) {
            System.err.println("Tentativa de enviar e-mail de status com dados incompletos.");
            return;
        }

        String recipientAddress = produtor.getEmail();
        String subject;
        String messageText;

        String status = novoStatus.equalsIgnoreCase("STATUS_ATIVO") ? "Aprovada" : "Rejeitada";

        subject = "CEASA Digital - Status da sua Solicitação de Venda";
        messageText = "Olá " + produtor.getName() + ",\n\n"
                + "Sua solicitação para vender o produto '" + produto.getNome() + "' foi avaliada.\n\n"
                + "Status: " + status + "\n\n";

        if (novoStatus.equalsIgnoreCase("STATUS_ATIVO")) {
            messageText += "Agora você já pode criar ofertas para este produto!\n\n";
        } else { // STATUS_REJEITADO
            messageText += "Infelizmente, sua solicitação não foi aprovada neste momento. Se tiver dúvidas, entre em contato com a administração.\n\n";
        }
        messageText += "Atenciosamente,\nEquipe CEASA Digital";
        sendSimpleMail(recipientAddress, subject, messageText);
    }
}
