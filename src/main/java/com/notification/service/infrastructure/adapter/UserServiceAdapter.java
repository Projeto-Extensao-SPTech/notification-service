package com.notification.service.infrastructure.adapter;

import com.notification.service.domain.gateway.UserServiceGateway;
import com.notification.service.infrastructure.client.UserServiceClient;

import java.util.List;

public class UserServiceAdapter implements UserServiceGateway {

    private final UserServiceClient userServiceClient;

    public UserServiceAdapter(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public List<String> getUsersToNotification() {
        return userServiceClient.getUsersToNotification();
    }
}
