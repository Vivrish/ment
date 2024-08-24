package com.example.chatservice.DTO;

import com.example.chatservice.domain.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortMessageDto {
    private String message;
    private LocalDateTime timeStamp;
    private String roomName;
    private String senderName;
    public ShortMessageDto(MessageEntity messageEntity) {
        this.message = messageEntity.getMessage();
        this.timeStamp = messageEntity.getTimeStamp();
        this.roomName = messageEntity.getRoom().getName();
        this.senderName = messageEntity.getSender().getNickname();
    }
}
