package com.notification_service.infrastructure.controller;

import com.notification_service.application.dto.NotificationMapperDTO;
import com.notification_service.application.dto.request.CreateNotificationRequest;
import com.notification_service.application.dto.response.CreateNotificationResponse;
import com.notification_service.application.dto.response.NotificationResponse;
import com.notification_service.application.usecases.notification.CreateNotification;
import com.notification_service.application.usecases.notification.DeleteNotification;
import com.notification_service.application.usecases.notification.GetNotificationById;
import com.notification_service.domain.entity.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/notification")
@RestController
public class NotificationController {

    private final CreateNotification createNotificationUseCase;
    private final GetNotificationById getNotificationById;
    private final DeleteNotification deleteNotification;
    private final NotificationMapperDTO notificationMapperDTO;

    public NotificationController(
            CreateNotification createNotificationUseCase,
            NotificationMapperDTO notificationMapperDTO,
            GetNotificationById getNotificationById,
            DeleteNotification deleteNotification
    ) {
        this.createNotificationUseCase = createNotificationUseCase;
        this.notificationMapperDTO = notificationMapperDTO;
        this.getNotificationById = getNotificationById;
        this.deleteNotification = deleteNotification;
    }

    @PostMapping
    private ResponseEntity<CreateNotificationResponse> createNotification(@RequestBody CreateNotificationRequest notificationRequest) {

        Notification notificationCreateObj = notificationMapperDTO.toDomain(notificationRequest);
        Notification notification = createNotificationUseCase.execute(notificationCreateObj);
        var response = notificationMapperDTO.toResponse(notification);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id){
        var response = getNotificationById.execute(id);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        deleteNotification.execute(id);
    }
}

