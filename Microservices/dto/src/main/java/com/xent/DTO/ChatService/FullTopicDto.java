package com.xent.DTO.ChatService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullTopicDto {
    private String name;
    private ShortRoomDto room = null;


}
