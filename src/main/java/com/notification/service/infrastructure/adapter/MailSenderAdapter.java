package com.notification.service.infrastructure.adapter;

import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.exception.mail.MailSenderException;
import com.notification.service.domain.gateway.MailSenderGateway;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.util.List;

public class MailSenderAdapter implements MailSenderGateway {

    private final JavaMailSender mailSender;

    public MailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendSimpleMail(Notification notification, String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(notification.getNotificationType().getDescription());
            message.setText(notification.getMessage());
            mailSender.send(message);
        } catch (Exception e) {
            throw new MailSenderException("Error sending mail to " + to + ": " + e.getMessage());
        }
    }

    @Override
    public void sendBulkMail(Notification notification, List<String> recipients) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(recipients.toArray(new String[0]));
            helper.setSubject(notification.getNotificationType().getDescription());
            helper.setText(notification.getMessage(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new MailSenderException("Error sending bulk mail: " + e.getMessage());
        }
    }
}
