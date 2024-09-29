package com.xent.DTO.ChatService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullMessageDto {
    private String message;
    private ShortRoomDto room;
    private ShortChatUserDto sender;
    private LocalDateTime timeStamp;

}
