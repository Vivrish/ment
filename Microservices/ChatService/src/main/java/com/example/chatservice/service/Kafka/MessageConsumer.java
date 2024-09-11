package com.example.chatservice.service.Kafka;

import com.example.chatservice.DTO.FullUserDto;
import com.example.chatservice.DTO.ShortMessageDto;

import com.example.chatservice.DTO.ShortUserDto;
import com.example.chatservice.service.MessageService;
import com.example.chatservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class MessageConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topicPattern = "topic-room-.*", groupId = "main")
    public void consumeRoomMessage(ShortMessageDto message) {
        log.debug("Consumed message on topic topic-room: {}", message);
        messagingTemplate.convertAndSend(String.format("/topic/room/%s", message.getRoomName()), message.getMessage());
        messageService.addMessage(message);
        log.debug("Message consumed: {}", message.getMessage());
    }

    @KafkaListener(topics = "register", groupId = "main")
    public void consumeRegister(String message) throws JsonProcessingException {
        log.debug("Deserializing message: {}", message);
        FullUserDto fullUser = objectMapper.readValue(message, FullUserDto.class);
        log.debug("Deserialized: {}", fullUser);
        log.debug("Consumed message on topic register: {}", fullUser);
        userService.addUser(new ShortUserDto(fullUser));
    }




}
