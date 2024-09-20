package com.example.apigateway.service;

import com.xent.DTO.ChatService.ShortMessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService {
    private final KafkaTemplate<String, ShortMessageDto> messageTemplate;
    public void sendMessage(ShortMessageDto message) {
        log.debug("Sending message using HTTP and kafka: {}", message);
        messageTemplate.send("send-message-http", message);
    }

}
