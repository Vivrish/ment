package com.example.chatservice.DTO;

import com.example.chatservice.domain.TopicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortTopicDto {
    private String name;
    private String roomName;

    public ShortTopicDto(TopicEntity topicEntity) {
        this.name = topicEntity.getTopicName();
        this.roomName = topicEntity.getRoom().getName();
    }
}
