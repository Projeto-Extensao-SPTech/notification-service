package com.notification.service.application.web.controllers;

import com.notification.service.application.dto.NotificationMapperDTO;
import com.notification.service.application.dto.request.CreateNotificationRequest;
import com.notification.service.application.dto.response.CreateNotificationResponse;
import com.notification.service.application.dto.response.NotificationResponse;
import com.notification.service.application.dto.response.PageResponse;
import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.usecases.notification.CreateNotification;
import com.notification.service.domain.usecases.notification.DeleteNotification;
import com.notification.service.domain.usecases.notification.GetNotification;
import com.notification.service.domain.usecases.notification.GetNotificationById;
import com.notification.service.domain.usecases.notification.GetNotificationByRecurrence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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
    private ResponseEntity<CreateNotificationResponse> createNotification(
            @RequestBody CreateNotificationRequest notificationRequest
    ) {

        Notification notificationCreateObj = notificationMapperDTO.toDomain(notificationRequest);
        Notification notification = createNotificationUseCase.execute(
                notificationCreateObj,
                notificationRequest.recurrence()
        );
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
    private PageResponse<NotificationResponse> getAllNotification(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        Pageable requestPageable = PageRequest.of(page, size);
        Page<NotificationResponse> responsePage = getNotification.execute(requestPageable);
        return new PageResponse<>(responsePage);
    }

    @GetMapping("/recurrence")
    public PageResponse<NotificationResponse> findByRecurrenceDate(
            @RequestParam LocalDate date,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        Pageable requestPageable = PageRequest.of(page, size);
        Page<NotificationResponse> response =
                getNotificationByRecurrence.execute(date, requestPageable);

        return new PageResponse<>(response);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        deleteNotification.execute(id);
    }
}

