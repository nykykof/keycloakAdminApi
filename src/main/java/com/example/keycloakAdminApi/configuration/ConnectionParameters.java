package com.example.keycloakAdminApi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "keycloakParam")
@Getter
@Setter
@Configuration
public class ConnectionParameters {

    private String serverUrl;
    private String realm;
    private String username;
    private String password;
    private  String clientId;
    private String grantType;

}
