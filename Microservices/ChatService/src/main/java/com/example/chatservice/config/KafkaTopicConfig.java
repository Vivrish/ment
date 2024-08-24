package com.example.chatservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


public class KafkaTopicConfig {

    public NewTopic getTopic() {
        return TopicBuilder.name("main").build();
    }
}
