package com.notification.service.infrastructure.schedule;

import com.notification.service.domain.usecases.notification.SendScheduleNotifications;
import org.springframework.scheduling.annotation.Scheduled;

public class SendBulkMailSchedule {
    private final SendScheduleNotifications sendScheduleNotifications;

    public SendBulkMailSchedule(SendScheduleNotifications sendScheduleNotifications) {
        this.sendScheduleNotifications = sendScheduleNotifications;
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void execute() {
        sendScheduleNotifications.execute();
    }
}
