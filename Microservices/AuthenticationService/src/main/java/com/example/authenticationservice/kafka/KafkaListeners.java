package com.example.authenticationservice.kafka;

import com.example.authenticationservice.service.AuthenticationService;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaListeners {
    private final AuthenticationService authenticationService;

    @KafkaListener(topics = "register", groupId = "authenticationService")
    public void register(FullUserDto fullUserDto) {
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto(fullUserDto);
        log.debug("Registering user: {}", userCredentialsDto);
        authenticationService.register(userCredentialsDto);
    }
}
