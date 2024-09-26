package com.example.chatservice.service.Kafka;

import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.UserEntity;
import com.example.chatservice.exception.RoomDoesNotExistException;
import com.example.chatservice.exception.UserDoesNotExistException;
import com.example.chatservice.exception.UserIsNotAMemberException;
import com.example.chatservice.repository.RoomRepository;
import com.example.chatservice.repository.UserRepository;
import com.xent.DTO.ChatService.ShortMessageDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MessageProducer {
    private final KafkaTemplate<String, ShortMessageDto> kafkaTemplate;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public void sendMessage(ShortMessageDto message) {
        RoomEntity roomEntity = roomRepository.findByName(message.getRoomName()).orElseThrow(RoomDoesNotExistException::new);
        UserEntity userEntity = userRepository.findByNickname(message.getSenderName()).orElseThrow(UserDoesNotExistException::new);
        if (!roomEntity.getMembers().contains(userEntity)) {
            throw new UserIsNotAMemberException();
        }
        log.debug("Sending message {} to room {}", message.getMessage(), message.getRoomName());
        kafkaTemplate.send(roomEntity.getTopic().getTopicName(), message.getSenderName(), message);
        log.debug("Message is sent successfully");
    }





}
