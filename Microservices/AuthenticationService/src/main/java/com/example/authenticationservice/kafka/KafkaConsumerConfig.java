package com.example.authenticationservice.kafka;

import com.xent.DTO.APIGateway.FailureDto;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.Constants.KafkaMessageType;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, FullUserDto> consumerFactoryUser() {
        return new DefaultKafkaConsumerFactory<>(generateProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FullUserDto> kafkaListenerContainerFactoryUser() {
        ConcurrentKafkaListenerContainerFactory<String, FullUserDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryUser());
        factory.setRecordFilterStrategy(record -> {
            String messageTypeHeader = new String(record.headers().lastHeader("messageType").value());
            return !KafkaMessageType.USER_REGISTRATION.getMessageType().equals(messageTypeHeader);
        });
        return factory;
    }

    @Bean
    public ConsumerFactory<String, FailureDto> consumerFactoryFailure() {
        return new DefaultKafkaConsumerFactory<>(generateProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FailureDto> kafkaListenerContainerFactoryFailure() {
        ConcurrentKafkaListenerContainerFactory<String, FailureDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryFailure());
        factory.setRecordFilterStrategy(record -> {
            String messageTypeHeader = new String(record.headers().lastHeader("messageType").value());
            return !KafkaMessageType.USER_REGISTRATION_FAILURE.getMessageType().equals(messageTypeHeader);
        });
        return factory;
    }

    private Map<String, Object> generateProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "authenticationService");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }


}