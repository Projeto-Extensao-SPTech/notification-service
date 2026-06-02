package com.notification.service.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository <NotificationEntity, Long> {
    boolean existsByEventId(String eventId);

    Optional<NotificationEntity> findByEventId(String eventId);
}
