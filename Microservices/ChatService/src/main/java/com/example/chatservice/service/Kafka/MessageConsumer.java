package com.example.chatservice.service.Kafka;

import com.example.chatservice.service.MessageService;
import com.example.chatservice.service.UserService;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.ChatService.ShortMessageDto;
import com.xent.DTO.ChatService.ShortChatUserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class MessageConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final UserService userService;


    @KafkaListener(topicPattern = "topic-room-.*", groupId = "main", containerFactory = "kafkaListenerContainerFactoryMessage")
    public void consumeRoomMessage(ShortMessageDto message) {
        log.debug("Consumed message on topic topic-room: {}", message);
        messagingTemplate.convertAndSend(String.format("/topic/room/%s", message.getRoomName()), message.getMessage());
        messageService.addMessage(message);
        log.debug("Message consumed: {}", message.getMessage());
    }

    @KafkaListener(topics = "register", groupId = "main", containerFactory = "kafkaListenerContainerFactoryUser")
    public void consumeRegister(FullUserDto fullUserToRegister) {
        log.debug("Consumed message on topic register: {}", fullUserToRegister);
        userService.addUser(new ShortChatUserDto(fullUserToRegister));
    }




}
