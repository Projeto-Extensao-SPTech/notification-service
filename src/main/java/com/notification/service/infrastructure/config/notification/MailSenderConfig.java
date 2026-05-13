package com.notification.service.infrastructure.config.notification;

import com.notification.service.domain.gateway.MailSenderGateway;
import com.notification.service.infrastructure.adapter.MailSenderAdapter;
import com.notification.service.infrastructure.config.environment.EnvironmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    private final EnvironmentService environmentService;

    public MailSenderConfig(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environmentService.getProperty("mail.host", String.class));
        mailSender.setPort(environmentService.getProperty("mail.port", Integer.class));
        mailSender.setUsername(environmentService.getProperty("mail.username", String.class));
        mailSender.setPassword(environmentService.getProperty("mail.password", String.class));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

    @Bean
    public MailSenderGateway mailSenderGateway(
            JavaMailSender javaMailSender
    ) {
        return new MailSenderAdapter(javaMailSender);
    }
}
