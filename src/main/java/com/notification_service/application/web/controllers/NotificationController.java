package com.notification_service.application.web.controllers;

import com.notification_service.application.dto.NotificationMapperDTO;
import com.notification_service.application.dto.request.CreateNotificationRequest;
import com.notification_service.application.dto.response.CreateNotificationResponse;
import com.notification_service.application.dto.response.NotificationResponse;
import com.notification_service.application.usecases.notification.*;
import com.notification_service.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/notifications")
@RestController
public class NotificationController {

    private final CreateNotification createNotificationUseCase;
    private final GetNotificationById getNotificationById;
    private final GetNotification getNotification;
    private final GetNotificationByRecurrence getNotificationByRecurrence;
    private final DeleteNotification deleteNotification;
    private final NotificationMapperDTO notificationMapperDTO;

    public NotificationController(
            CreateNotification createNotificationUseCase,
            NotificationMapperDTO notificationMapperDTO,
            GetNotificationById getNotificationById,
            GetNotificationByRecurrence getNotificationByRecurrence,
            DeleteNotification deleteNotification,
            GetNotification getNotification
    ) {
        this.createNotificationUseCase = createNotificationUseCase;
        this.notificationMapperDTO = notificationMapperDTO;
        this.getNotificationById = getNotificationById;
        this.getNotificationByRecurrence = getNotificationByRecurrence;
        this.deleteNotification = deleteNotification;
        this.getNotification = getNotification;
    }

    @PostMapping
    private ResponseEntity<CreateNotificationResponse> createNotification(@RequestBody CreateNotificationRequest notificationRequest) {

        Notification notificationCreateObj = notificationMapperDTO.toDomain(notificationRequest);
        Notification notification = createNotificationUseCase.execute(notificationCreateObj, notificationRequest.recurrence());
        var response = notificationMapperDTO.toResponse(notification);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id) {
        var response = getNotificationById.execute(id);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    private Page<NotificationResponse> getAllNotification(Pageable pageable) {
        return getNotification.execute(pageable);
    }

    @GetMapping("/recurrence")
    public ResponseEntity<List<NotificationResponse>> findByRecurrenceDate(
            @RequestParam LocalDate date
    ) {
        List<NotificationResponse> response =
                getNotificationByRecurrence.execute(date);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        deleteNotification.execute(id);
    }
}

