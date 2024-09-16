package com.xent.DTO.ChatService;


import com.xent.DTO.APIGateway.FullUserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class ShortChatUserDto {
    private String username;
    private Collection<String> messages = new ArrayList<>();
    private Collection<String> roomNames = new ArrayList<>();
    private Collection<String> connectedRoomNames = new ArrayList<>();


    public ShortChatUserDto(FullChatUserDto fullChatUser) {
        this.username = fullChatUser.getUsername();
        for (ShortMessageDto messageEntity: fullChatUser.getMessages()){
            messages.add(messageEntity.getMessage());
        }
        for (ShortRoomDto roomEntity: fullChatUser.getRooms()) {
            roomNames.add(roomEntity.getName());
        }
        for (ShortRoomDto roomEntity: fullChatUser.getConnections()) {
            connectedRoomNames.add(roomEntity.getName());
        }
    }

    public ShortChatUserDto(FullUserDto fullUser) {
        this.username = fullUser.getUsername();
        for (ShortMessageDto messageEntity: fullUser.getMessages()){
            messages.add(messageEntity.getMessage());
        }
        for (ShortRoomDto roomEntity: fullUser.getRooms()) {
            roomNames.add(roomEntity.getName());
        }
        for (ShortRoomDto roomEntity: fullUser.getConnectedRooms()) {
            connectedRoomNames.add(roomEntity.getName());
        }

    }

    @Override
    public String toString() {
        return "ShortChatUserDto{" +
                "username='" + username + '\'' +
                ", messages=" + messages +
                ", roomNames=" + roomNames +
                ", connectedRoomNames=" + connectedRoomNames +
                '}';
    }
}
