package com.xent.DTO.ChatService;


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

    public ShortMessageDto(FullMessageDto fullMessageDto) {
        this.message = fullMessageDto.getMessage();
        this.timeStamp = fullMessageDto.getTimeStamp();
        if (fullMessageDto.getRoom() != null) {
            this.roomName = fullMessageDto.getRoom().getName();
        }
        if (fullMessageDto.getSender() != null) {
            this.senderName = fullMessageDto.getSender().getUsername();
        }
    }


    @Override
    public String toString() {
        return "ShortMessageDto{" +
                "message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                ", roomName='" + roomName + '\'' +
                ", senderName='" + senderName + '\'' +
                '}';
    }
}
