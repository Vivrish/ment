package com.example.usermanagementservice.kafka;

import com.example.usermanagementservice.service.UserService;
import com.xent.DTO.APIGateway.FailureDto;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.Constants.KafkaMessageType;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
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
    private final UserService userService;
    private final KafkaTemplate<String, FailureDto> failureTemplate;
    @KafkaListener(topics = "register", groupId = "userManagementServiceRegister", containerFactory = "kafkaListenerContainerFactoryUser")
    public void register(FullUserDto fullUserToRegister) {
        log.debug("Registering user: {}", fullUserToRegister);
        try {
            userService.createUser(new FullUserDetailsDto(fullUserToRegister));
        }
        catch (Exception e) {
            FailureDto failure = new FailureDto("UserManagementService", e.toString(), fullUserToRegister.getUsername());
            log.error("Error while registering user. Initiating failure procedure: {}", fullUserToRegister);
            failureTemplate.send(MessageBuilder
                    .withPayload(failure)
                    .setHeader(KafkaHeaders.TOPIC, "register")
                    .setHeader(KafkaHeaders.KEY, fullUserToRegister.getUsername())
                    .setHeader("messageType", KafkaMessageType.USER_REGISTRATION_FAILURE.getMessageType())
                    .build());
        }

    }

    @KafkaListener(topics = "register", groupId = "userManagementServiceRegisterFailure", containerFactory = "kafkaListenerContainerFactoryFailure")
    public void rollback(FailureDto failure) throws InterruptedException {
        Thread.sleep(5000);
        log.info("Rolling back register: {}", failure);
        String username = failure.getRollbackIdentification();
        userService.deleteUserIfExists(username);
    }
}
