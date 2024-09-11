package com.example.chatservice.DTO;

import com.example.chatservice.domain.MessageEntity;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class ShortUserDto {
    private String username;
    private Collection<String> messages = new ArrayList<>();
    private Collection<String> roomNames = new ArrayList<>();
    private Collection<String> connectedRoomNames = new ArrayList<>();

    public ShortUserDto(UserEntity userEntity) {
        this.username = userEntity.getNickname();
        for (MessageEntity messageEntity: userEntity.getMessages()){
            messages.add(messageEntity.getMessage());
        }
        for (RoomEntity roomEntity: userEntity.getRooms()) {
            roomNames.add(roomEntity.getName());
        }
        for (RoomEntity roomEntity: userEntity.getConnections()) {
            connectedRoomNames.add(roomEntity.getName());
        }
    }


    public ShortUserDto(FullUserDto fullUser) {
        this.username = fullUser.getUsername();
        for (ShortMessageDto messageEntity: fullUser.getMessages()){
            messages.add(messageEntity.getMessage());
        }
        for (ShortRoomDto roomEntity: fullUser.getRooms()) {
            roomNames.add(roomEntity.getName());
        }
        for (ShortRoomDto roomEntity: fullUser.getConnections()) {
            connectedRoomNames.add(roomEntity.getName());
        }
    }

    @Override
    public String toString() {
        return "ShortUserDto{" +
                "username='" + username + '\'' +
                ", messages=" + messages +
                ", roomNames=" + roomNames +
                ", connectedRoomNames=" + connectedRoomNames +
                '}';
    }
}
