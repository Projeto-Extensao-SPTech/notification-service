package com.notification.service.domain.exception.mail;

import org.springframework.http.HttpStatus;

public class MailSenderException extends RuntimeException {
    private final HttpStatus status;

    public MailSenderException(String message) {
        super(message);
        this.status = HttpStatus.UNPROCESSABLE_CONTENT;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
