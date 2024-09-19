package com.example.chatservice.service.Kafka;

import com.example.chatservice.service.MessageService;
import com.example.chatservice.service.RoomService;
import com.example.chatservice.service.UserService;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.ChatService.ShortMessageDto;
import com.xent.DTO.ChatService.ShortChatUserDto;
import com.xent.DTO.APIGateway.FailureDto;
import com.xent.DTO.ChatService.ShortRoomDto;
import com.xent.DTO.Constants.KafkaMessageType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class MessageConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final UserService userService;
    private final RoomService roomService;
    private final KafkaTemplate<String, FailureDto> failureTemplate;



    @KafkaListener(topicPattern = "topic-room-.*", groupId = "main", containerFactory = "kafkaListenerContainerFactoryMessage")
    public void consumeRoomMessage(ShortMessageDto message) {
        log.debug("Consumed message on topic topic-room: {}", message);
        messagingTemplate.convertAndSend(String.format("/topic/room/%s", message.getRoomName()), message.getMessage());
        messageService.addMessage(message);
        log.debug("Message consumed: {}", message.getMessage());
    }

    @KafkaListener(topics = "register", groupId = "chatService", containerFactory = "kafkaListenerContainerFactoryUser")
    public void consumeRegister(FullUserDto fullUserToRegister) {
        log.debug("Consumed message on topic register: {}", fullUserToRegister);
        try {
            userService.addUser(new ShortChatUserDto(fullUserToRegister));
        }
        catch (Exception e) {
            FailureDto failure = new FailureDto("ChatService", e.toString(), fullUserToRegister.getUsername());
            log.error("Error while registering user. Initiating failure procedure: {}", fullUserToRegister);
            failureTemplate.send(MessageBuilder
                    .withPayload(failure)
                    .setHeader(KafkaHeaders.TOPIC, "register")
                    .setHeader(KafkaHeaders.KEY, fullUserToRegister.getUsername())
                    .setHeader("messageType", KafkaMessageType.USER_REGISTRATION_FAILURE.getMessageType())
                    .build());
        }
    }

    @KafkaListener(topics = "register", groupId = "chatService", containerFactory = "kafkaListenerContainerFactoryFailure")
    public void rollBackRegister(FailureDto failure) {
        log.info("Rolling back the registration: {}", failure);
        String username = failure.getRollbackIdentification();
        userService.deleteUserIfExists(username);
    }



    @KafkaListener(topics = "create-room", groupId = "chatService", containerFactory = "kafkaListenerContainerFactoryNewRoom")
    public void createRoom(ShortRoomDto room) {
        log.info("Creating new room: {}", room);
        roomService.createRoom(room);
    }

    @KafkaListener(topicPattern = "send-message-http", groupId = "chatService", containerFactory = "kafkaListenerContainerFactoryMessage")
    public void sendMessageHttp(ShortMessageDto message) {
        log.info("Adding new message via HTTP");
        messageService.addMessage(message);
    }



}
