package com.notification.service.domain.entity;

public enum NotificationType {
    FAIR("Feira de adoção se aproximando"),
    DONATION("Precisamos de doações"),
    GENERAL("Abrigo Dog Feliz quer falar com você"),
    VOLUNTEER("Precisamos de voluntários"),
    SPONSORSHIP("Precisamos de patrocínio"),
    UPDATE_PASSWORD("Atualize sua senha");

    private final String description;
    public String getDescription() {
        return description;
    }

    NotificationType(String description) {
        this.description = description;
    }
}
