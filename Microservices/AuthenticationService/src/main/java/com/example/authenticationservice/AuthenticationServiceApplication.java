package com.example.authenticationservice;

import com.example.authenticationservice.DTO.RoleDto;
import com.example.authenticationservice.DTO.UserCredentialsDto;
import com.example.authenticationservice.service.AuthenticationService;
import com.example.authenticationservice.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class AuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AuthenticationService authenticationService, RoleService roleService) {
		return args -> {
			roleService.addRole(new RoleDto("Admin"));
			authenticationService.register(new UserCredentialsDto("Antonio", "password", List.of(new RoleDto[]{new RoleDto("Admin")})));
			System.out.println(authenticationService.getAllUsers().get(0).getNickname());
			System.out.println(authenticationService.getAllUsers().get(0).getRoles());

		};
	}

	@Bean
	BCryptPasswordEncoder getEncoder() {
		// 12 rounds of hashing
		return new BCryptPasswordEncoder(12);
	}



}
