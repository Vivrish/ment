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
public class FullChatUserDto {
    private String nickname;
    private Collection<ShortMessageDto> messages = new ArrayList<>();
    private Collection<ShortRoomDto> rooms = new ArrayList<>();
    private Collection<ShortRoomDto> connections = new ArrayList<>();

    public FullChatUserDto(UserEntity userEntity) {
        this.nickname = userEntity.getNickname();
        for (MessageEntity messageEntity: userEntity.getMessages()) {
            this.messages.add(new ShortMessageDto(messageEntity));
        }
        for (RoomEntity roomEntity: userEntity.getRooms()) {
            this.rooms.add(new ShortRoomDto(roomEntity));
        }
        for (RoomEntity roomEntity: userEntity.getConnections()) {
            this.connections.add(new ShortRoomDto(roomEntity));
        }
    }
}
