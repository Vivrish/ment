package com.example.apigateway.service;


import com.example.apigateway.DTO.FullUserCredentialsDto;
import com.example.apigateway.DTO.FullUserDetailsDto;
import com.example.apigateway.DTO.FullUserDto;
import com.example.apigateway.feignClients.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final AuthenticationService authenticationService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void addUser(@NonNull FullUserDto userDto) {
        FullUserCredentialsDto userCredentials = new FullUserCredentialsDto(userDto);
        log.debug("Sending POST request to auth service: {}", userDto);
        authenticationService.register(userCredentials);
        log.debug("Adding user to the register topic: {}", userDto);
        kafkaTemplate.send("register", new FullUserDetailsDto(userDto));
    }

    public String login(@NonNull FullUserCredentialsDto credentials) {
        log.debug("Trying to log in user: {}", credentials);
        return authenticationService.login(credentials);
    }

    public Collection<FullUserDto> getAllUsers(String authHeader) {
        authenticate(authHeader);
        Collection<FullUserDto> users = new ArrayList<>();


        return users;
    }

    private void authenticate(String authHeader) {
        if (!authenticationService.authenticate(authHeader)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401));
        }
    }

}
