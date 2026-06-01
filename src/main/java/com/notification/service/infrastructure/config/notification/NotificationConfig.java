package com.notification.service.infrastructure.config.notification;

import com.notification.service.application.dto.NotificationMapperDTO;
import com.notification.service.domain.gateway.MailSenderGateway;
import com.notification.service.domain.gateway.NotificationRepositoryGateway;
import com.notification.service.domain.service.RecurrenceCalculator;
import com.notification.service.domain.usecases.notification.CreateNotification;
import com.notification.service.domain.usecases.notification.DeleteNotification;
import com.notification.service.domain.usecases.notification.GetNotification;
import com.notification.service.domain.usecases.notification.GetNotificationById;
import com.notification.service.domain.usecases.notification.GetNotificationByRecurrence;
import com.notification.service.domain.usecases.notification.SendInstantNotification;
import com.notification.service.domain.usecases.notification.SendScheduleNotifications;
import com.notification.service.infrastructure.mapper.NotificationEntityMapper;
import com.notification.service.infrastructure.adapter.NotificationRepositoryAdapter;
import com.notification.service.infrastructure.persistence.NotificationRecurrenceRepository;
import com.notification.service.infrastructure.persistence.NotificationRepository;
import com.notification.service.infrastructure.queue.listener.NotificationInstantListener;
import com.notification.service.infrastructure.queue.listener.NotificationScheduledListener;
import com.notification.service.infrastructure.schedule.SendBulkMailSchedule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    @Bean
    CreateNotification createNotificationCase(
            NotificationRepositoryGateway notificationRepositoryGateway,
            RecurrenceCalculator recurrenceCalculator
    ) {
        return new CreateNotification(notificationRepositoryGateway, recurrenceCalculator);
    }

    @Bean
    GetNotificationById getNotificationById(
            NotificationRepositoryGateway notificationRepositoryGateway
    ) {
        return new GetNotificationById(notificationRepositoryGateway);
    }

    @Bean
    GetNotification getNotification(
            NotificationRepositoryGateway notificationRepositoryGateway
    ) {
        return new GetNotification(notificationRepositoryGateway);
    }

    @Bean
    GetNotificationByRecurrence getNotificationByRecurrence(
            NotificationRepositoryGateway notificationRepositoryGateway
    ) {
        return new GetNotificationByRecurrence(notificationRepositoryGateway);
    }

    @Bean
    DeleteNotification deleteNotification(
            NotificationRepositoryGateway notificationRepositoryGateway
    ) {
        return new DeleteNotification(notificationRepositoryGateway);
    }

    @Bean
    SendInstantNotification sendInstantNotification(
            NotificationRepositoryGateway notificationRepositoryGateway,
            MailSenderGateway mailSenderGateway
    ) {
        return new SendInstantNotification(notificationRepositoryGateway, mailSenderGateway);
    }

    @Bean
    NotificationRepositoryGateway notificationGateway(
            NotificationRepository notificationRepository,
            NotificationEntityMapper notificationEntityMapper,
            NotificationRecurrenceRepository notificationRecurrenceRepository
    ) {
        return new NotificationRepositoryAdapter(
                notificationRepository,
                notificationEntityMapper,
                notificationRecurrenceRepository
        );
    }

    @Bean
    NotificationEntityMapper notificationEntityMapper() {
        return new NotificationEntityMapper();
    }

    @Bean
    NotificationMapperDTO notificationMapperDTO() {
        return new NotificationMapperDTO();
    }

    @Bean
    RecurrenceCalculator recurrenceCalculator() {
        return new RecurrenceCalculator();
    }

    @Bean
    NotificationInstantListener notificationInstantListener(
            SendInstantNotification sendInstantNotification
    ) {
        return new NotificationInstantListener(sendInstantNotification);
    }

    @Bean
    NotificationScheduledListener notificationScheduledListener(
            CreateNotification createNotification
    ) {
        return new NotificationScheduledListener(
                createNotification
        );
    }

    @Bean
    SendScheduleNotifications sendScheduleNotifications(
            MailSenderGateway mailSenderGateway
    ) {
        return new SendScheduleNotifications(
                mailSenderGateway
        );
    }

    @Bean
    SendBulkMailSchedule sendBulkMailSchedule(
            SendScheduleNotifications sendScheduleNotifications
    ) {
        return new SendBulkMailSchedule(
                sendScheduleNotifications
        );
    }
}
