package com.example.chatservice.service;

import com.example.chatservice.DTO.Converter;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.TopicEntity;
import com.example.chatservice.domain.UserEntity;
import com.example.chatservice.exception.RoomDoesNotExistException;
import com.example.chatservice.exception.UserAlreadyAMemberException;
import com.example.chatservice.exception.UserDoesNotExistException;
import com.example.chatservice.exception.UserIsNotAMemberException;
import com.example.chatservice.repository.RoomRepository;
import com.example.chatservice.repository.TopicRepository;
import com.example.chatservice.repository.UserRepository;
import com.xent.DTO.ChatService.FullRoomDto;
import com.xent.DTO.ChatService.ShortRoomDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final KafkaAdmin kafkaAdmin;
    private final Converter converter;

    public FullRoomDto getRoomByName(String name) {
        RoomEntity roomEntity = getRoomOrThrow(name);
        return converter.fullRoomDto(roomEntity);
    }

    public Collection<FullRoomDto> getAll() {
        Collection<FullRoomDto> rooms = new ArrayList<>();
        for (RoomEntity roomEntity: roomRepository.findAll()) {
            rooms.add(converter.fullRoomDto(roomEntity));
        }
        return rooms;
    }

    public FullRoomDto createRoom(ShortRoomDto shortRoomDto) {
        RoomEntity roomEntity = new RoomEntity(shortRoomDto);
        roomRepository.save(roomEntity);
        TopicEntity topicEntity = new TopicEntity(roomEntity);
        topicRepository.save(topicEntity);
        roomEntity.setTopic(topicEntity);
        createTopic(topicEntity.getTopicName());
        return converter.fullRoomDto(roomRepository.save(roomEntity));
    }

    public FullRoomDto editRoom(String name, ShortRoomDto shortRoomDto) {
        RoomEntity roomEntity = getRoomOrThrow(name);
        roomEntity.setFields(shortRoomDto);
        return converter.fullRoomDto(roomRepository.save(roomEntity));
    }

    public void deleteRoom(String name) {
        if (!roomRepository.existsByName(name)) {
            throw new RoomDoesNotExistException();
        }
        roomRepository.deleteByName(name);
    }




    public FullRoomDto addMember(String roomName, String username) {
        UserEntity userEntity = userRepository.findByNickname(username)
                .orElseThrow(UserDoesNotExistException::new);
        RoomEntity roomEntity = roomRepository.findByName(roomName)
                .orElseThrow(RoomDoesNotExistException::new);
        if (roomEntity.getMembers().contains(userEntity)) {
            throw new UserAlreadyAMemberException();
        }
        roomEntity.addMember(userEntity);
        return converter.fullRoomDto(roomRepository.save(roomEntity));
    }

    public FullRoomDto deleteMember(String roomName, String username) {
        UserEntity userEntity = userRepository.findByNickname(username)
                .orElseThrow(UserDoesNotExistException::new);
        RoomEntity roomEntity = roomRepository.findByName(roomName)
                .orElseThrow(RoomDoesNotExistException::new);
        if (!roomEntity.getMembers().contains(userEntity)) {
            throw new UserIsNotAMemberException();
        }
        roomEntity.deleteMember(userEntity);
        return converter.fullRoomDto(roomRepository.save(roomEntity));
    }

    private void createTopic(String topicName) {
        log.info("Creating new topic: {}", topicName);
        NewTopic topic = TopicBuilder.name(topicName).partitions(5).replicas(1).build();
        kafkaAdmin.createOrModifyTopics(topic);
    }

    private RoomEntity getRoomOrThrow(String name) throws RoomDoesNotExistException  {
        return roomRepository.findByName(name).orElseThrow(RoomDoesNotExistException::new);
    }


}
