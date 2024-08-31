package com.example.chatservice.service.Kafka;

import com.example.chatservice.DTO.ShortMessageDto;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.TopicEntity;
import com.example.chatservice.exception.RoomDoesNotExistException;
import com.example.chatservice.repository.RoomRepository;
import com.example.chatservice.repository.TopicRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MessageProducer {
    private final KafkaTemplate<String, ShortMessageDto> kafkaTemplate;
    private final RoomRepository roomRepository;

    public void sendMessage(ShortMessageDto message) {
        RoomEntity roomEntity = roomRepository.findByName(message.getRoomName()).orElseThrow(RoomDoesNotExistException::new);
        log.debug("Sending message {} to room {}", message.getMessage(), message.getRoomName());
        kafkaTemplate.send(roomEntity.getTopic().getTopicName(), message.getSenderName(), message);
        log.debug("Message is sent successfully");
    }





}
