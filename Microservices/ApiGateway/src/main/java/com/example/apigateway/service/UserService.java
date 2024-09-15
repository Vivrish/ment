package com.example.apigateway.service;


import com.example.apigateway.feignClients.AuthenticationService;
import com.example.apigateway.feignClients.ChatService;
import com.example.apigateway.feignClients.UserManagementService;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import com.xent.DTO.ChatService.FullChatUserDto;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final AuthenticationService authenticationService;
    private final UserManagementService userManagementService;
    private final ChatService chatService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void addUser(@NonNull FullUserDto userDto) {
        UserCredentialsDto userCredentials = new UserCredentialsDto(userDto);
        log.debug("Sending POST request to auth service: {}", userCredentials);
        authenticationService.register(userCredentials);
        log.debug("Adding user to the register topic: {}", userDto);
        kafkaTemplate.send("register", userDto);
    }

    public String login(@NonNull UserCredentialsDto credentials) {
        log.debug("Trying to log in user: {}", credentials);
        return authenticationService.login(credentials);
    }

    public FullUserDto getUserByName(String username, String authHeader) {
        authenticate(authHeader);
        FullUserDto userDto = new FullUserDto();
        log.info("Getting full user information for {}", username);
        UserCredentialsDto credentialsDto = authenticationService.getUserByName(username);
        FullUserDetailsDto detailsDto = userManagementService.getUserByName(username);
        FullChatUserDto chatUserDto = chatService.getUserByName(username);
        userDto.updateAttributes(credentialsDto);
        userDto.updateAttributes(detailsDto);
        userDto.updateAttributes(chatUserDto);
        log.info("All info is gathered: {}", userDto);
        return userDto;
    }

    private void authenticate(String authHeader) {
        log.info("Authorizing header: {}", authHeader);
        if (!authenticationService.authenticate(authHeader)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401));
        }
    }
}
