package com.example.keycloakAdminApi.configuration;

import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectorConfig {

    private final ConnectionParameters connectionParameters;
    @Bean
    public Keycloak keycloakConnectore(){

        return KeycloakBuilder
                .builder()
                .serverUrl(connectionParameters.getServerUrl())
                .realm(connectionParameters.getRealm())
                .grantType(connectionParameters.getGrantType())
                .clientId(connectionParameters.getClientId())
                .username(connectionParameters.getUsername())
                .password(connectionParameters.getPassword())
                .resteasyClient(
                      new  ResteasyClientBuilder()
                        .connectionPoolSize(10)

                        .connectionCheckoutTimeout(10, TimeUnit.SECONDS)
                        .build()
                ).build();
    }
}
