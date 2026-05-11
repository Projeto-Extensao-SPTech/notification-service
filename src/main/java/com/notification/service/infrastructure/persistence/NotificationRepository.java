package com.notification.service.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository <NotificationEntity, Long> {
}
