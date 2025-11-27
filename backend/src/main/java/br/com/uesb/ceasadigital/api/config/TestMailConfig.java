package br.com.uesb.ceasadigital.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@Profile("test")
public class TestMailConfig {

    /**
     * Cria um JavaMailSender mockado para testes que não envia emails reais.
     * Apenas permite que a aplicação inicie sem necessidade de configuração de email.
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Configuração mínima apenas para permitir criação de MimeMessage
        mailSender.setSession(Session.getInstance(new Properties()));
        
        // Sobrescreve o método send para não enviar emails de verdade
        return new JavaMailSender() {
            @Override
            public MimeMessage createMimeMessage() {
                return mailSender.createMimeMessage();
            }

            @Override
            public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
                return mailSender.createMimeMessage(contentStream);
            }

            @Override
            public void send(MimeMessage mimeMessage) throws MailException {
                // Não faz nada em testes - não envia email real
            }

            @Override
            public void send(MimeMessage... mimeMessages) throws MailException {
                // Não faz nada em testes - não envia emails reais
            }

            @Override
            public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
                // Não faz nada em testes - não envia email real
            }

            @Override
            public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
                // Não faz nada em testes - não envia emails reais
            }

            @Override
            public void send(SimpleMailMessage simpleMessage) throws MailException {
                // Não faz nada em testes - não envia email real
            }

            @Override
            public void send(SimpleMailMessage... simpleMessages) throws MailException {
                // Não faz nada em testes - não envia emails reais
            }
        };
    }
}

