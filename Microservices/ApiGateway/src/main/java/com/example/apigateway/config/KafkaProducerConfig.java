package com.example.apigateway.config;

import com.xent.DTO.APIGateway.FailureDto;
import com.xent.DTO.APIGateway.FullUserDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Bean
    public ProducerFactory<String, FullUserDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(generateConfigProps());
    }

    @Bean
    public KafkaTemplate<String, FullUserDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, FailureDto> producerFactoryFailure() {
        return new DefaultKafkaProducerFactory<>(generateConfigProps());
    }

    @Bean
    public KafkaTemplate<String, FailureDto> kafkaTemplateFailure() {
        return new KafkaTemplate<>(producerFactoryFailure());
    }

    private Map<String, Object> generateConfigProps() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }



}
