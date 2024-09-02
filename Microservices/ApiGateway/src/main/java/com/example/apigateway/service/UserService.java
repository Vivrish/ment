package com.example.apigateway.service;


import com.example.apigateway.DTO.FullUserCredentialsDto;
import com.example.apigateway.DTO.FullUserDto;
import com.example.apigateway.feignClients.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final AuthenticationService authenticationService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void addUser(@NonNull FullUserDto userDto) {
        FullUserCredentialsDto userCredentials = new FullUserCredentialsDto(userDto);
        log.debug("Sending POST request to auth service: {}, {}", userDto.getUsername(), userDto.getPassword());
        authenticationService.register(userCredentials);
        kafkaTemplate.send("register", userDto);
    }
}
