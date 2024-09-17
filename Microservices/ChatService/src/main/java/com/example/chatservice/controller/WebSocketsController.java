package com.example.chatservice.controller;


import com.example.chatservice.service.Kafka.MessageProducer;

import com.example.chatservice.service.Kafka.TopicService;
import com.xent.DTO.ChatService.FullTopicDto;
import com.xent.DTO.ChatService.ShortMessageDto;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@AllArgsConstructor
@Slf4j
public class WebSocketsController {
    private final MessageProducer messageProducer;
    private final TopicService topicService;


    @MessageMapping("/sendMessage")
    @SendTo("/topic/room/{roomName}")
    public void sendMessage(@Payload ShortMessageDto message) {
        if (message == null) {
            log.error("Message is null");
            return;
        }
        messageProducer.sendMessage(message);
        log.debug("Message accepted from {}: {}", message.getSenderName(), message.getMessage());
    }

    @GetMapping("/api/v1/topics")
    public Collection<FullTopicDto> getTopics() {
         return topicService.getAllTopics();
    }

}
