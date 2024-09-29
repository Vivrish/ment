package com.example.chatservice.service.Kafka;

import com.example.chatservice.DTO.Converter;
import com.example.chatservice.domain.TopicEntity;
import com.example.chatservice.repository.TopicRepository;
import com.xent.DTO.ChatService.FullTopicDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final Converter converter;

    public Collection<FullTopicDto> getAllTopics() {
        Collection<FullTopicDto> topics = new ArrayList<>();
        for (TopicEntity topicEntity: topicRepository.findAll()) {
            topics.add(converter.fullTopicDto(topicEntity));
        }
        return topics;
    }
}
