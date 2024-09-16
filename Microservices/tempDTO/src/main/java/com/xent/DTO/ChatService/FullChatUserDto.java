package com.xent.DTO.ChatService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullChatUserDto {
    private String username;
    private Collection<ShortMessageDto> messages = new ArrayList<>();
    private Collection<ShortRoomDto> rooms = new ArrayList<>();
    private Collection<ShortRoomDto> connections = new ArrayList<>();

    public void addMessage(ShortMessageDto messageDto) {
        messages.add(messageDto);
    }
    public void addRoom(ShortRoomDto roomDto) {
        rooms.add(roomDto);
    }
    public void addConnectedRoom(ShortRoomDto roomDto) {
        connections.add(roomDto);
    }

}
