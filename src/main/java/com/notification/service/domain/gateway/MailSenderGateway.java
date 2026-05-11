package com.notification.service.domain.gateway;

import com.notification.service.domain.entity.Notification;
import java.util.List;

public interface MailSenderGateway {
    void sendSimpleMail(Notification notification, String to);

    void sendBulkMail(Notification notification, List<String> recipients);
}
