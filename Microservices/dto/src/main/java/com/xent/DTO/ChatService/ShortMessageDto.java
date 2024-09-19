package com.xent.DTO.ChatService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public ShortMessageDto(String message, String roomName, String senderName) {
        this.message = message;
        this.roomName = roomName;
        this.senderName = senderName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortMessageDto that = (ShortMessageDto) o;
        return Objects.equals(message, that.message) && Objects.equals(roomName, that.roomName) && Objects.equals(senderName, that.senderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, roomName, senderName);
    }
}
