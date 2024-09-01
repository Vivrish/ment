package com.example.chatservice.service;

import com.example.chatservice.DTO.FullMessageDto;
import com.example.chatservice.DTO.ShortMessageDto;
import com.example.chatservice.domain.MessageEntity;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.UserEntity;
import com.example.chatservice.exception.MessageDoesNotExistException;
import com.example.chatservice.exception.RoomDoesNotExistException;
import com.example.chatservice.exception.UserDoesNotExistException;
import com.example.chatservice.repository.MessageRepository;
import com.example.chatservice.repository.RoomRepository;
import com.example.chatservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public FullMessageDto addMessage(ShortMessageDto shortMessageDto) {
        UserEntity sender = userRepository.findByNickname(shortMessageDto.getSenderName())
                .orElseThrow(UserDoesNotExistException::new);
        RoomEntity roomEntity = roomRepository.findByName(shortMessageDto.getRoomName())
                .orElseThrow(RoomDoesNotExistException::new);

        MessageEntity messageEntity = new MessageEntity(shortMessageDto);
        messageEntity.setRoom(roomEntity);
        messageEntity.setSender(sender);
        log.debug("Message saved: {}", shortMessageDto.getMessage());
        return new FullMessageDto(messageRepository.save(messageEntity));
    }

    public FullMessageDto editMessage(Long id, ShortMessageDto shortMessageDto) {
        MessageEntity messageEntity = getMessageOrThrow(id);
        messageEntity.setFields(shortMessageDto);
        return new FullMessageDto(messageRepository.save(messageEntity));
    }

    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new MessageDoesNotExistException();
        }
        messageRepository.deleteById(id);
    }

    public FullMessageDto getMessage(Long id) {
        MessageEntity messageEntity = getMessageOrThrow(id);
        return new FullMessageDto(messageEntity);
    }

    private MessageEntity getMessageOrThrow(Long id) {
        return messageRepository.findById(id).orElseThrow(MessageDoesNotExistException::new);
    }

    public Collection<FullMessageDto> getAll() {
        Collection<FullMessageDto> messages = new ArrayList<>();
        for (MessageEntity messageEntity: messageRepository.findAll()) {
            messages.add(new FullMessageDto(messageEntity));
        }
        return messages;
    }
}
