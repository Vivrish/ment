package com.example.usermanagementservice.kafka;

import com.example.usermanagementservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaListeners {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService;
    @KafkaListener(topics = "register", groupId = "main")
    public void register(String message) throws JsonProcessingException {
        log.debug("Deserializing JSON: {}", message);
        FullUserDetailsDto userDto = objectMapper.readValue(message, FullUserDetailsDto.class);
        log.debug("Deserialized successfully: {}", userDto);
        userService.createUser(userDto);
    }
}
