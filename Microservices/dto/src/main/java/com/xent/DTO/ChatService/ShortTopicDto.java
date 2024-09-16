package com.xent.DTO.ChatService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortTopicDto {
    private String name;
    private String roomName;

    public ShortTopicDto(FullTopicDto fullTopicDto) {
        this.name = fullTopicDto.getName();
        if (fullTopicDto.getRoom() != null) {
            this.roomName = fullTopicDto.getRoom().getName();
        }
    }
}
