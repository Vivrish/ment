package com.example.chatservice.DTO;

import com.example.chatservice.domain.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullMessageDto {
    private String message;
    private ShortRoomDto room;
    private ShortUserDto sender;

    public FullMessageDto(MessageEntity messageEntity) {
        this.message = messageEntity.getMessage();
        this.room = new ShortRoomDto(messageEntity.getRoom());
        this.sender = new ShortUserDto(messageEntity.getSender());
    }
}
