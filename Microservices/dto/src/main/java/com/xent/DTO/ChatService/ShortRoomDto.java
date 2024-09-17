package com.xent.DTO.ChatService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortRoomDto {
    private String name;
    private String topicName;
    private Collection<String> memberNames = new ArrayList<>();
    private Collection<String> messages = new ArrayList<>();
    private Collection<String> connectedMemberNames = new ArrayList<>();


    public ShortRoomDto(FullRoomDto fullRoomDto) {
        this.name = fullRoomDto.getName();
        if (fullRoomDto.getTopic() != null) {
            this.topicName = fullRoomDto.getTopic().getName();
        }
        for (ShortMessageDto messageDto: fullRoomDto.getMessages()) {
            this.messages.add(messageDto.getMessage());
        }
        for (ShortChatUserDto chatUserDto: fullRoomDto.getUsers()) {
            this.memberNames.add(chatUserDto.getUsername());
        }
        for (ShortChatUserDto chatUserDto: fullRoomDto.getConnectedMembers()) {
            this.connectedMemberNames.add(chatUserDto.getUsername());
        }
    }
}
