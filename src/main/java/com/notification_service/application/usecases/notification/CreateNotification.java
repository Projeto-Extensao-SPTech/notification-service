package com.notification_service.application.usecases.notification;

import com.notification_service.application.gateway.NotificationGateway;
import com.notification_service.domain.entity.Notification;
import com.notification_service.domain.service.RecurrenceCalculator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class CreateNotification {

    private final NotificationGateway notificationGateway;
    private final RecurrenceCalculator recurrenceCalculator;

    public CreateNotification(NotificationGateway notificationGateway,
                              RecurrenceCalculator recurrenceCalculator) {
        this.notificationGateway = notificationGateway;
        this.recurrenceCalculator = recurrenceCalculator;
    }

    public Notification execute(Notification notification, List<Integer> recurrences) {

        Notification savedNotification =
                notificationGateway.createNotification(notification);

        if (recurrences != null) {
            Set<LocalDate> recurrenceDates =
                    recurrenceCalculator.buildRecurrenceDates(notification.getEventDate(), recurrences);

            notificationGateway.createRecurrences(savedNotification, recurrenceDates);
        }

        return savedNotification;
    }
}

