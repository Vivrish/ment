package com.example.authenticationservice.kafka;

import com.example.authenticationservice.service.AuthenticationService;
import com.xent.DTO.APIGateway.FailureDto;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import com.xent.DTO.Constants.KafkaMessageType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaListeners {
    private final AuthenticationService authenticationService;
    private final KafkaTemplate<String, FailureDto> failureTemplate;
    @KafkaListener(topics = "register", groupId = "authenticationServiceRegister", containerFactory = "kafkaListenerContainerFactoryUser")
    public void register(FullUserDto fullUserDto) {
        try {
            UserCredentialsDto userCredentialsDto = new UserCredentialsDto(fullUserDto);
            log.debug("Registering user: {}", userCredentialsDto);
            authenticationService.register(userCredentialsDto);
        }
        catch (Exception e) {
            FailureDto failure = new FailureDto("AuthService", e.toString(), fullUserDto.getUsername());
            log.error("Error while registering user. Initiating failure procedure: {}", fullUserDto);
            failureTemplate.send(MessageBuilder
                    .withPayload(failure)
                    .setHeader(KafkaHeaders.TOPIC, "register")
                    .setHeader(KafkaHeaders.KEY, fullUserDto.getUsername())
                    .setHeader("messageType", KafkaMessageType.USER_REGISTRATION_FAILURE.getMessageType())
                    .build());
        }
    }
    @KafkaListener(topics = "register", groupId = "authenticationServiceRegister", containerFactory = "kafkaListenerContainerFactoryFailure")
    public void rollBackRegister(FailureDto failure) {
        log.info("Rolling back register: {}", failure);
        String username = failure.getRollbackIdentification();
        authenticationService.deleteUserIfExists(username);
    }
}
