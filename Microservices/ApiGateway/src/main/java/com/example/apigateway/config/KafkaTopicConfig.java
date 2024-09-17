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
    NewTopic registerStatus() {
        return TopicBuilder.name("register-failure")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
