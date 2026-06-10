package com.notification.service.domain.usecases.notification;

import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.gateway.MailSenderGateway;
import com.notification.service.domain.gateway.NotificationRepositoryGateway;
import com.notification.service.domain.gateway.UserServiceGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class SendScheduleNotifications {
    private final MailSenderGateway mailSenderGateway;

    private final NotificationRepositoryGateway notificationRepositoryGateway;

    private final UserServiceGateway userServiceGateway;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SendScheduleNotifications(
            MailSenderGateway mailSenderGateway,
            NotificationRepositoryGateway notificationRepositoryGateway, UserServiceGateway userServiceGateway
    ) {
        this.mailSenderGateway = mailSenderGateway;
        this.notificationRepositoryGateway = notificationRepositoryGateway;
        this.userServiceGateway = userServiceGateway;
    }

    public void execute() {
        logger.info("Starting send bulk mail scheduled process");
        try {
            List<Notification> notifications = notificationRepositoryGateway
                    .findNotificationRecurrences(LocalDate.now());

            if (!notifications.isEmpty()) {
                List<String> recipients = userServiceGateway
                        .getUsersToNotification();
                notifications
                        .forEach(
                                it -> mailSenderGateway.sendBulkMail(it, recipients)
                        );
                logger.info(
                        "Successfully send bulk mail: notificationsSend={}, toUsers={}",
                        notifications.size(),
                        recipients.size()
                );
            } else {
                logger.info("Does not exists scheduled notifications to send");
            }
        } catch (Exception e) {
            logger.error(
                    "Error in send bulk mail: message={}, cause={}",
                    e.getMessage(),
                    e.getCause()
            );
            throw new RuntimeException(e);
        }
    }
}
