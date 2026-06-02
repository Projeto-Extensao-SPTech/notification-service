package com.notification.service.domain.exception.notification;

import org.springframework.http.HttpStatus;

public class NotificationNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public NotificationNotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
