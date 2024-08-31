package com.example.chatservice.service.Kafka;

import com.example.chatservice.DTO.ShortMessageDto;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.TopicEntity;
import com.example.chatservice.exception.RoomDoesNotExistException;
import com.example.chatservice.repository.RoomRepository;
import com.example.chatservice.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageProducer {
    private final KafkaTemplate<String, ShortMessageDto> kafkaTemplate;
    private final RoomRepository roomRepository;

    public void sendMessage(ShortMessageDto message) {
        RoomEntity roomEntity = roomRepository.findByName(message.getRoomName()).orElseThrow(RoomDoesNotExistException::new);
        System.out.printf("Sending message %s to room %s%n", message.getMessage(), message.getRoomName());
        kafkaTemplate.send(roomEntity.getTopic().getTopicName(), message.getSenderName(), message);
        System.out.println("Message is sent successfully");
    }





}
