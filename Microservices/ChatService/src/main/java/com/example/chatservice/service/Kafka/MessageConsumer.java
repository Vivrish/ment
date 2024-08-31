package com.example.chatservice.service.Kafka;

import com.example.chatservice.DTO.ShortMessageDto;

import com.example.chatservice.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MessageConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @KafkaListener(topicPattern = "topic-room-.*", groupId = "main")
    public void consume(ShortMessageDto message) {
        messagingTemplate.convertAndSend(String.format("/topic/room/%s", message.getRoomName()), message.getMessage());
        messageService.addMessage(message);
        System.out.printf("Message consumed: %s%n", message.getMessage());
    }




}
