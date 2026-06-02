package com.notification.service.domain.usecases.notification;

import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.gateway.MailSenderGateway;
import com.notification.service.domain.gateway.NotificationRepositoryGateway;

public class SendInstantNotification {

    private final NotificationRepositoryGateway notificationRepositoryGateway;

    private final MailSenderGateway mailSenderGateway;

    public SendInstantNotification(
            NotificationRepositoryGateway notificationRepositoryGateway,
            MailSenderGateway mailSenderGateway
    ) {
        this.notificationRepositoryGateway = notificationRepositoryGateway;
        this.mailSenderGateway = mailSenderGateway;
    }

    public Notification execute(Notification notification) {
        if (notificationRepositoryGateway.existsByEventId(notification.getEventId())) {
            return notificationRepositoryGateway.findByEventId(notification.getEventId());
        }

        Notification toSave = notification.getMessage() != null &&
                (notification.getMessage().contains("<html") || notification.getMessage().contains("<!DOCTYPE"))
                ? notification.withoutMessage()
                : notification;

        Notification savedNotification = notificationRepositoryGateway.createNotification(toSave);
        mailSenderGateway.sendSimpleMail(notification);
        return savedNotification;
    }
}
