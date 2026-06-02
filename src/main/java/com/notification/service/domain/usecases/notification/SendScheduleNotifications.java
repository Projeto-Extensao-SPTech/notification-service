package com.notification.service.domain.usecases.notification;

import com.notification.service.domain.gateway.MailSenderGateway;

public class SendScheduleNotifications {
    private final MailSenderGateway mailSenderGateway;

    public SendScheduleNotifications(MailSenderGateway mailSenderGateway) {
        this.mailSenderGateway = mailSenderGateway;
    }

    public void execute() {

    }
}
