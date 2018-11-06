package com.example.keycloakAdminApi.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeycloakUserService {

    private final Keycloak  keycloakServer;

    @HystrixCommand(fallbackMethod = "getUsersFallback")
    public Optional<List<UserRepresentation>> getUsers(){
        return Optional.of(keycloakServer.realm("nowcp").users().list());
    }

    public Optional<List<UserRepresentation>> getUsersFallback(){
        UserRepresentation user = new UserRepresentation();
        user.setUsername("server unavailable");
        return Optional.of(Arrays.asList(user));
    }
}
