package com.example.usermanagementservice.kafka;

import com.example.usermanagementservice.service.UserService;
import com.xent.DTO.APIGateway.FailureDto;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaListeners {
    private final UserService userService;
    private final KafkaTemplate<String, FailureDto> failureTemplate;
    @KafkaListener(topics = "register", groupId = "userManagementService", containerFactory = "kafkaListenerContainerFactoryUser")
    public void register(FullUserDto fullUserToRegister) {
        log.debug("Registering user: {}", fullUserToRegister);
        try {
            userService.createUser(new FullUserDetailsDto(fullUserToRegister));
        }
        catch (Exception e) {
            FailureDto failure = new FailureDto("UserManagementService", e, fullUserToRegister.getUsername());
            log.error("Error while registering user. Initiating failure procedure: {}", fullUserToRegister);
            failureTemplate.send("register-failure", failure);
        }

    }

    @KafkaListener(topics = "register-failure", groupId = "userManagementService", containerFactory = "kafkaListenerContainerFactoryFailure")
    public void rollback(FailureDto failure) {
        log.info("Rolling back register: {}", failure);
        String username = failure.getRollbackIdentification();
        userService.deleteUserIfExists(username);
    }
}
