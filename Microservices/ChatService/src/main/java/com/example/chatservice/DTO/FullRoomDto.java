package com.example.chatservice.DTO;

import com.example.chatservice.domain.MessageEntity;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullRoomDto {
    private String name;
    private ShortTopicDto topic;
    private Collection<ShortUserDto> users = new ArrayList<>();
    private Collection<ShortMessageDto> messages = new ArrayList<>();
    private Collection<ShortUserDto> connectedMembers = new ArrayList<>();

    public FullRoomDto(RoomEntity roomEntity) {
        this.name = roomEntity.getName();
        if (roomEntity.getTopic() == null) {
            this.topic = null;
        }
        else {
            this.topic = new ShortTopicDto(roomEntity.getTopic());
        }
        for (UserEntity userEntity: roomEntity.getMembers()) {
            this.users.add(new ShortUserDto(userEntity));
        }
        for (MessageEntity messageEntity: roomEntity.getMessages()) {
            this.messages.add(new ShortMessageDto(messageEntity));
        }
        for (UserEntity userEntity: roomEntity.getConnectedMembers()) {
            this.connectedMembers.add(new ShortUserDto(userEntity));
        }
    }
}
