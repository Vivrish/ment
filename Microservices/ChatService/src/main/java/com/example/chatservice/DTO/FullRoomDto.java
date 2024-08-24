package com.example.chatservice.DTO;

import com.example.chatservice.domain.MessageEntity;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class FullRoomDto {
    private String name;
    private Collection<ShortUserDto> users = new ArrayList<>();
    private Collection<ShortMessageDto> messages = new ArrayList<>();

    public FullRoomDto(RoomEntity roomEntity) {
        this.name = roomEntity.getName();
        for (UserEntity userEntity: roomEntity.getMembers()) {
            this.users.add(new ShortUserDto(userEntity));
        }
        for (MessageEntity messageEntity: roomEntity.getMessages()) {
            this.messages.add(new ShortMessageDto(messageEntity));
        }
    }
}
