package com.example.chatservice.service.Kafka;

import com.example.chatservice.DTO.FullTopicDto;
import com.example.chatservice.domain.TopicEntity;
import com.example.chatservice.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    public Collection<FullTopicDto> getAllTopics() {
        Collection<FullTopicDto> topics = new ArrayList<>();
        for (TopicEntity topicEntity: topicRepository.findAll()) {
            topics.add(new FullTopicDto(topicEntity));
        }
        return topics;
    }
}
