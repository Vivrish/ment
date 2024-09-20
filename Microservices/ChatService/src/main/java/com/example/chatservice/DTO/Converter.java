package com.example.chatservice.DTO;

import com.example.chatservice.domain.MessageEntity;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.TopicEntity;
import com.example.chatservice.domain.UserEntity;
import com.xent.DTO.ChatService.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

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
       ShortRoomDto shortRoomDto = new ShortRoomDto();
       shortRoomDto.setName(roomEntity.getName());
       if (roomEntity.getTopic() != null) {
           shortRoomDto.setTopicName(roomEntity.getTopic().getTopicName());
       }
       Collection<String> members = new ArrayList<>();
       Collection<String> connections = new ArrayList<>();
       Collection<String> messages = new ArrayList<>();
       for (MessageEntity messageEntity: roomEntity.getMessages()) {
           messages.add(messageEntity.getMessage());
       }
       for (UserEntity userEntity: roomEntity.getMembers()) {
           members.add(userEntity.getNickname());
       }
       for (UserEntity userEntity: roomEntity.getConnectedMembers()) {
           connections.add(userEntity.getNickname());
       }
       shortRoomDto.setMessages(messages);
       shortRoomDto.setMemberNames(members);
       shortRoomDto.setConnectedMemberNames(connections);
       return shortRoomDto;
    }
    public ShortChatUserDto shortChatUserDto(UserEntity user) {
        ShortChatUserDto shortChatUserDto = new ShortChatUserDto();
        shortChatUserDto.setUsername(user.getNickname());
        Collection<String> rooms = new ArrayList<>();
        Collection<String> messages = new ArrayList<>();
        Collection<String> connections = new ArrayList<>();
        for (RoomEntity roomEntity: user.getRooms()) {
            rooms.add(roomEntity.getName());
        }
        for (MessageEntity messageEntity: user.getMessages()) {
            messages.add(messageEntity.getMessage());
        }
        for (RoomEntity roomEntity: user.getConnections()) {
            connections.add(roomEntity.getName());
        }
        shortChatUserDto.setMessages(messages);
        shortChatUserDto.setRoomNames(rooms);
        shortChatUserDto.setConnectedRoomNames(connections);
        return shortChatUserDto;
    }

    public ShortTopicDto shortTopicDto(TopicEntity topicEntity) {
        ShortTopicDto shortTopicDto = new ShortTopicDto();
        shortTopicDto.setName(topicEntity.getTopicName());
        if (topicEntity.getRoom() != null) {
            shortTopicDto.setRoomName(topicEntity.getRoom().getName());
        }
        return shortTopicDto;
    }

    public ShortMessageDto shortMessageDto(MessageEntity messageEntity) {
        return new ShortMessageDto(
                messageEntity.getMessage(),
                messageEntity.getTimeStamp(),
                messageEntity.getRoom().getName(),
                messageEntity.getSender().getNickname());
    }

}
