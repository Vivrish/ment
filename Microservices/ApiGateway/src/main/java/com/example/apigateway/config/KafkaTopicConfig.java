package com.example.apigateway.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    NewTopic register() {
        return TopicBuilder.name("register")
                .partitions(1)
                .replicas(1)
                .build();
    }


    @Bean
    NewTopic createRoom() {
        return TopicBuilder.name("create-room")
                .partitions(1)
                .replicas(1)
                .build();
    }


    @Bean
    NewTopic sendMessageHttp() {
        return TopicBuilder.name("send-message-http")
                .partitions(1)
                .replicas(1)
                .build();
    }


    @Bean
    NewTopic addUserToRoom() {
        return TopicBuilder.name("add-user-to-room")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
