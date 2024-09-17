package com.xent.DTO.ChatService;

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
    private Collection<ShortChatUserDto> users = new ArrayList<>();
    private Collection<ShortMessageDto> messages = new ArrayList<>();
    private Collection<ShortChatUserDto> connectedMembers = new ArrayList<>();


    public void addMessage(ShortMessageDto shortMessageDto) {
        messages.add(shortMessageDto);
    }

    public void addMember(ShortChatUserDto shortChatUserDto) {
        users.add(shortChatUserDto);
    }

    public void addConnectedMember(ShortChatUserDto shortChatUserDto) {
        connectedMembers.add(shortChatUserDto);
    }
}
