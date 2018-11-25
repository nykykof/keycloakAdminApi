package com.example.keycloakAdminApi;

import com.example.keycloakAdminApi.service.KeycloakUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@Slf4j
@EnableHystrix
public class KeycloakAdminApiApplication implements CommandLineRunner {
	@Autowired
	KeycloakUserService userService;

	public static void main(String[] args) {
		SpringApplication.run(KeycloakAdminApiApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		//log.error("",userService.createClient("apikeyclient").getSecret());
		/*userService.getClient("apikeyclient").getProtocolMappers().forEach(it->
				{
					log.error("name: {}",it.getName());
					log.error("protocol mapper: {}",it.getProtocolMapper());
					log.error("config : {}", it.getConfig());
					log.error("protocol: {}",it.getProtocol());
				}
		);*/
log.info("access : {}",userService.getClient("apikeyclient").getAuthenticationFlowBindingOverrides());
		userService.getUsers().ifPresent(users->users.forEach(it->log.info("{}",it.getUsername())));
	}
}
