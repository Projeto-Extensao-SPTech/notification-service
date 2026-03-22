package com.notification_service.config.notification;

import com.notification_service.application.dto.NotificationMapperDTO;
import com.notification_service.application.gateway.NotificationGateway;
import com.notification_service.application.usecases.notification.CreateNotification;
import com.notification_service.application.usecases.notification.DeleteNotification;
import com.notification_service.application.usecases.notification.GetNotification;
import com.notification_service.application.usecases.notification.GetNotificationById;
import com.notification_service.domain.service.RecurrenceCalculator;
import com.notification_service.infrastructure.gateway.NotificationEntityMapper;
import com.notification_service.infrastructure.gateway.NotificationRepositoryGateway;
import com.notification_service.infrastructure.persistence.NotificationRecurrenceRepository;
import com.notification_service.infrastructure.persistence.NotificationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    @Bean
    CreateNotification createNotificationCase(NotificationGateway notificationGateway, RecurrenceCalculator recurrenceCalculator) {
        return new CreateNotification(notificationGateway,recurrenceCalculator);
    }

    @Bean
    GetNotificationById getNotificationById(NotificationGateway notificationGateway){
        return new GetNotificationById(notificationGateway);
    }

    @Bean
    GetNotification getNotification(NotificationGateway notificationGateway){
        return new GetNotification(notificationGateway);
    }

    @Bean
    DeleteNotification deleteNotification(NotificationGateway notificationGateway){
        return new DeleteNotification(notificationGateway);
    }

    @Bean
    NotificationGateway notificationGateway(NotificationRepository notificationRepository, NotificationEntityMapper notificationEntityMapper, NotificationRecurrenceRepository notificationRecurrenceRepository) {
        return new NotificationRepositoryGateway(notificationRepository, notificationEntityMapper, notificationRecurrenceRepository);
    }

    @Bean
    NotificationEntityMapper notificationEntityMapper() {
        return new NotificationEntityMapper();
    }

    @Bean
    NotificationMapperDTO notificationMapperDTO(){
        return new NotificationMapperDTO();
    }

    @Bean
    RecurrenceCalculator recurrenceCalculator(){return new RecurrenceCalculator();}
}
