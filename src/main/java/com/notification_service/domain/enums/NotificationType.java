package com.notification_service.domain.enums;

public enum NotificationType {
    FAIR("Feira de adoção se aproximando"),
    DONATION("Precisamos de doações"),
    GENERAL("Abrigo Dog Feliz quer falar com você"),
    VOLUNTEER("Precisamos de voluntários");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }

    private String getDescription() {
        return description;
    }
}
