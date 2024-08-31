package com.example.chatservice.controller;


import com.example.chatservice.DTO.FullTopicDto;
import com.example.chatservice.DTO.ShortMessageDto;
import com.example.chatservice.service.Kafka.MessageConsumer;
import com.example.chatservice.service.Kafka.MessageProducer;

import com.example.chatservice.service.Kafka.TopicService;
import lombok.AllArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@AllArgsConstructor
public class WebSocketsController {
    private final MessageProducer messageProducer;
    private final TopicService topicService;


    @MessageMapping("/sendMessage")
    @SendTo("/topic/room/{roomName}")
    public void sendMessage(@Payload ShortMessageDto message, SimpMessageHeaderAccessor accessor) {
        if (message == null) {
            System.out.println("Message is null");
            return;
        }
        messageProducer.sendMessage(message);
        System.out.printf("%s -- Message accepted from %s: %s", message.getTimeStamp(), message.getSenderName(), message.getMessage());
    }

    @GetMapping("/api/v1/topics")
    public Collection<FullTopicDto> getTopics() {
         return topicService.getAllTopics();
    }

}
