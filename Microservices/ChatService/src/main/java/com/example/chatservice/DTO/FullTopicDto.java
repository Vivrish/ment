package com.example.chatservice.DTO;

import com.example.chatservice.domain.TopicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullTopicDto {
    private String name;
    private ShortRoomDto room;

    public FullTopicDto(TopicEntity topicEntity) {
        this.name = topicEntity.getTopicName();
        this.room = new ShortRoomDto(topicEntity.getRoom());
    }
}
