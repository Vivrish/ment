package com.example.authenticationservice;

import com.example.authenticationservice.DTO.UserCredentialsDto;
import com.example.authenticationservice.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AuthenticationService authenticationService) {
		return args -> {
			authenticationService.register(new UserCredentialsDto("Antonio", "password"));
			System.out.println(authenticationService.getAllUsers().get(0).getName());
		};
	}

}
