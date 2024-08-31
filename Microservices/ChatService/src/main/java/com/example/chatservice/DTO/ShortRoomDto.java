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
public class ShortRoomDto {
    private String name;
    private String topicName;
    private Collection<String> memberNames = new ArrayList<>();
    private Collection<String> messages = new ArrayList<>();
    private Collection<String> connectedMemberNames = new ArrayList<>();

    public ShortRoomDto(RoomEntity roomEntity) {
        this.name = roomEntity.getName();
        if (roomEntity.getTopic() == null) {
            this.topicName = null;
        }
        else {this.topicName = roomEntity.getTopic().getTopicName();}
        for (UserEntity userEntity: roomEntity.getMembers()) {
            this.memberNames.add(userEntity.getNickname());
        }
        for (MessageEntity messageEntity: roomEntity.getMessages()) {
            this.messages.add(messageEntity.getMessage());
        }
        for (UserEntity userEntity: roomEntity.getConnectedMembers()) {
            this.connectedMemberNames.add(userEntity.getNickname());
        }
    }
}
