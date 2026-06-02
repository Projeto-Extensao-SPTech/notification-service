package com.notification.service.domain.usecases.notification;

import com.notification.service.domain.gateway.NotificationRepositoryGateway;
import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.service.RecurrenceCalculator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class CreateNotification {

    private final NotificationRepositoryGateway notificationRepositoryGateway;

    private final RecurrenceCalculator recurrenceCalculator;

    public CreateNotification(NotificationRepositoryGateway notificationRepositoryGateway,
                              RecurrenceCalculator recurrenceCalculator) {
        this.notificationRepositoryGateway = notificationRepositoryGateway;
        this.recurrenceCalculator = recurrenceCalculator;
    }

    public Notification execute(Notification notification, List<Integer> recurrences) {
        if (notificationRepositoryGateway.existsByEventId(notification.getEventId())) {
            return notificationRepositoryGateway.findByEventId(notification.getEventId());
        }

        Notification savedNotification =
                notificationRepositoryGateway.createNotification(notification);

        if (recurrences != null) {
            Set<LocalDate> recurrenceDates =
                    recurrenceCalculator.buildRecurrenceDates(notification.getEventDate(), recurrences);

            notificationRepositoryGateway.createRecurrences(savedNotification, recurrenceDates);
        }

        return savedNotification;
    }
}

