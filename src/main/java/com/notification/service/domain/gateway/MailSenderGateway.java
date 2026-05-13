package com.notification.service.domain.gateway;

import com.notification.service.domain.entity.Notification;
import java.util.List;

public interface MailSenderGateway {
    void sendSimpleMail(Notification notification);

    void sendBulkMail(Notification notification, List<String> recipients);
}
