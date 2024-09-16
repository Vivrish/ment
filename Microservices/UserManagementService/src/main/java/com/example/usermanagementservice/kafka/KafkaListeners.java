package com.example.usermanagementservice.kafka;

import com.example.usermanagementservice.service.UserService;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaListeners {
    private final UserService userService;
    @KafkaListener(topics = "register", groupId = "userManagementService")
    public void register(FullUserDto fullUserToRegister) {
        log.debug("Registering user: {}", fullUserToRegister);
        userService.createUser(new FullUserDetailsDto(fullUserToRegister));
    }
}
