package com.example.chatservice.DTO;

import com.example.chatservice.domain.MessageEntity;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.TopicEntity;
import com.example.chatservice.domain.UserEntity;
import com.xent.DTO.ChatService.*;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    public FullTopicDto fullTopicDto(TopicEntity topicEntity) {
        FullTopicDto fullTopicDto = new FullTopicDto();
        fullTopicDto.setName(topicEntity.getTopicName());
        fullTopicDto.setRoom(shortRoomDto(topicEntity.getRoom()));
        return fullTopicDto;
    }

    public FullMessageDto fullMessageDto(MessageEntity messageEntity) {
        FullMessageDto fullMessageDto = new FullMessageDto();
        fullMessageDto.setMessage(messageEntity.getMessage());
        fullMessageDto.setRoom(shortRoomDto(messageEntity.getRoom()));
        fullMessageDto.setSender(shortChatUserDto(messageEntity.getSender()));
        fullMessageDto.setTimeStamp(messageEntity.getTimeStamp());
        return fullMessageDto;
    }

    public FullRoomDto fullRoomDto(RoomEntity roomEntity) {
        FullRoomDto fullRoomDto = new FullRoomDto();
        fullRoomDto.setName(roomEntity.getName());
        fullRoomDto.setTopic(shortTopicDto(roomEntity.getTopic()));
        for (MessageEntity messageEntity: roomEntity.getMessages()) {
            fullRoomDto.addMessage(shortMessageDto(messageEntity));
        }
        for (UserEntity userEntity: roomEntity.getMembers()) {
            fullRoomDto.addMember(shortChatUserDto(userEntity));
        }
        for (UserEntity userEntity: roomEntity.getConnectedMembers()) {
            fullRoomDto.addConnectedMember(shortChatUserDto(userEntity));
        }
        return fullRoomDto;
    }

    public FullChatUserDto fullChatUserDto(UserEntity user) {
        FullChatUserDto fullChatUserDto = new FullChatUserDto();
        fullChatUserDto.setUsername(user.getNickname());
        for (MessageEntity messageEntity: user.getMessages()) {
            fullChatUserDto.addMessage(shortMessageDto(messageEntity));
        }
        for (RoomEntity roomEntity: user.getRooms()) {
            fullChatUserDto.addRoom(shortRoomDto(roomEntity));
        }
        for (RoomEntity roomEntity: user.getConnections()) {
            fullChatUserDto.addConnectedRoom(shortRoomDto(roomEntity));
        }
        return fullChatUserDto;
    }

    public ShortRoomDto shortRoomDto(RoomEntity roomEntity) {
        FullRoomDto fullRoomDto = fullRoomDto(roomEntity);
        return new ShortRoomDto(fullRoomDto);
    }
    public ShortChatUserDto shortChatUserDto(UserEntity user) {
        FullChatUserDto fullChatUserDto = fullChatUserDto(user);
        return new ShortChatUserDto(fullChatUserDto);
    }

    public ShortTopicDto shortTopicDto(TopicEntity topicEntity) {
        FullTopicDto fullTopicDto = fullTopicDto(topicEntity);
        return new ShortTopicDto(fullTopicDto);
    }

    public ShortMessageDto shortMessageDto(MessageEntity messageEntity) {
        FullMessageDto fullMessageDto = fullMessageDto(messageEntity);
        return new ShortMessageDto(fullMessageDto);
    }

}
