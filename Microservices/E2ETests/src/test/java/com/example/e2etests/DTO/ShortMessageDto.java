package com.example.e2etests.DTO;

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
}