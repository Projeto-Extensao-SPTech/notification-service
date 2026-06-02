package com.notification.service.infrastructure.adapter;

import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.exception.mail.MailSenderException;
import com.notification.service.domain.gateway.MailSenderGateway;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.util.List;

public class MailSenderAdapter implements MailSenderGateway {

    private final JavaMailSender mailSender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendSimpleMail(Notification notification) {
        logger.info("Starting instant mail send process: notification={}", notification);
        String recipientMailAddress = notification.getRecipientMailAddress();
        try {
            String message = notification.getMessage();
            boolean isHtml = message != null && message.contains("<html") || message.contains("<!DOCTYPE");

            if (isHtml) {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setTo(recipientMailAddress);
                helper.setSubject(notification.getNotificationType().getDescription());
                helper.setText(message, true);
                mailSender.send(mimeMessage);
            } else {
                SimpleMailMessage simpleMessage = new SimpleMailMessage();
                simpleMessage.setTo(recipientMailAddress);
                simpleMessage.setSubject(notification.getNotificationType().getDescription());
                simpleMessage.setText(message);
                mailSender.send(simpleMessage);
            }

        } catch (Exception e) {
            logger.error("Fail to send mail: notificationId={}, eventId={}, errorMessage={}",
                    notification.getId(),
                    notification.getEventId(),
                    e.getMessage()
            );
            throw new MailSenderException("Error sending mail to " + recipientMailAddress + ": " + e.getMessage());
        }
        logger.info("Message sent successfully by notification={}", notification);
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
