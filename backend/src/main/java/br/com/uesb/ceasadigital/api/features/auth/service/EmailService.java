package br.com.uesb.ceasadigital.api.features.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    // Você pode comentar a injeção do JavaMailSender se não for usá-lo durante os testes
    // @Autowired
    // private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        // Log que será impresso no console em ambiente de desenvolvimento
        logger.info("======================================================");
        logger.info("SIMULANDO ENVIO DE E-MAIL (não enviado de verdade):");
        logger.info("PARA: {}", to);
        logger.info("ASSUNTO: {}", subject);
        logger.info("CONTEÚDO (link/token): {}", text); // Alterado para clareza
        logger.info("======================================================");

        // O código abaixo tentaria enviar um e-mail real.
        // Mantenha comentado ou remova se estiver apenas simulando.
        // Se suas configurações de application-dev.yml estiverem vazias, ele pode falhar
        // ou simplesmente não fazer nada, dependendo da versão do Spring Boot.
        /*
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            // mailSender.send(message); // Descomente esta linha para tentar o envio real
            logger.info("-> Tentativa de envio real realizada (verifique se as configurações SMTP estão corretas no .env e application.yml).");
        } catch (Exception e) {
            logger.error("-> Falha ao tentar enviar e-mail real: {}", e.getMessage());
        }
        */
    }
}