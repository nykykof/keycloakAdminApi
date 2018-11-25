package com.example.keycloakAdminApi.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.ProtocolMapperRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ClientRepresentation createClient(String name){
        ClientRepresentation client = new ClientRepresentation();

        client.setDirectAccessGrantsEnabled(false);
        client.setEnabled(true);
        client.setStandardFlowEnabled(false);
        client.setSecret("secret");
        client.setPublicClient(false);
        client.setId(name);
        client.setBearerOnly(false);
        client.setServiceAccountsEnabled(true);
        String[] roles = {"ROLE_ACCESS"};
        //client.setDefaultRoles(roles);
        ProtocolMapperRepresentation mapper = new ProtocolMapperRepresentation();
        mapper.setName("np-mapper");
        mapper.setProtocol("openid-connect");
        mapper.setProtocolMapper("oidc-script-based-protocol-mapper");
        Map<String,String> config = new HashMap<>();
        config.put("script","token.setOtherClaims(\"np\",{firstName:user.getFirstName()})");
        config.put("userinfo.token.claim","false");
        config.put("id.token.claim","false");
        config.put("access.token.claim","true");
        config.put("claim.name","np");
        config.put("jsonType.label","String");
        config.put("multivalued","false");
        mapper.setConfig(config);
        client.setProtocolMappers(Arrays.asList(mapper));

        RealmRepresentation realm = new RealmRepresentation();

        realm.setId("realm");
        realm.setEnabled(true);
        realm.setDisplayName("realm");
        realm.setRealm("realm");
        realm.setClients(Collections.singletonList(client));
        realm.getClients();
        keycloakServer.realms().create(realm);

        return keycloakServer.realm("realm").clients().findByClientId(name).get(0);
    }

    public ClientRepresentation getClient(String name){
        return keycloakServer.realms().realm("realm").clients().findByClientId(name).get(0);
    }

}
