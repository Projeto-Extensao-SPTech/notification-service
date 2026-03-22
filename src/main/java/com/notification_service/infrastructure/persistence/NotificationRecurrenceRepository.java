package com.notification_service.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRecurrenceRepository extends JpaRepository<NotificationRecurrenceEntity, Long> {
}
