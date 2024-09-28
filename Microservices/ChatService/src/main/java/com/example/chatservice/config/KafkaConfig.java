package com.example.chatservice.config;

import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.APIGateway.FailureDto;
import com.xent.DTO.APIGateway.ShortUserAndRoomDto;
import com.xent.DTO.ChatService.ShortMessageDto;
import com.xent.DTO.ChatService.ShortRoomDto;
import com.xent.DTO.Constants.KafkaMessageType;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS_CONFIG;

    // Configuration for topics that use ShorMessageDto
    @Bean
    public ProducerFactory<String, ShortMessageDto> producerFactoryMessage() {
        return new DefaultKafkaProducerFactory<>(generateConfigProps("ShortMessageDtoChatService"));
    }

    @Bean
    public KafkaTemplate<String, ShortMessageDto> kafkaTemplateMessage() {
        return new KafkaTemplate<>(producerFactoryMessage());
    }

    @Bean
    public ConsumerFactory<String, ShortMessageDto> consumerFactoryMessage() {
        return new DefaultKafkaConsumerFactory<>(generateProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShortMessageDto> kafkaListenerContainerFactoryMessage() {
        ConcurrentKafkaListenerContainerFactory<String, ShortMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryMessage());
        return factory;
    }

    // Configuration for topics that use FullUserDto

    @Bean
    public ProducerFactory<String, FullUserDto> producerFactoryUser() {
        return new DefaultKafkaProducerFactory<>(generateConfigProps("FullUserDtoChatService"));
    }

    @Bean
    public KafkaTemplate<String, FullUserDto> kafkaTemplateUser() {
        return new KafkaTemplate<>(producerFactoryUser());
    }

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

    // Configuration for topics that use FailureDto

    @Bean
    public ProducerFactory<String, FailureDto> producerFactoryFailure() {
        return new DefaultKafkaProducerFactory<>(generateConfigProps("FailureDtoChatService"));
    }

    @Bean
    public KafkaTemplate<String, FailureDto> kafkaTemplateFailure() {
        return new KafkaTemplate<>(producerFactoryFailure());
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

    // Configuration for topics that use ShortRoomDto
    @Bean
    public ConsumerFactory<String, ShortRoomDto> consumerFactoryNewRoom() {
        return new DefaultKafkaConsumerFactory<>(generateProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShortRoomDto> kafkaListenerContainerFactoryNewRoom() {
        ConcurrentKafkaListenerContainerFactory<String, ShortRoomDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryNewRoom());
        return factory;
    }

    // Configuration for topics that use ShortUserAndRoomDto
    @Bean
    public ConsumerFactory<String, ShortUserAndRoomDto> consumerFactoryAddUserToRoom() {
        return new DefaultKafkaConsumerFactory<>(generateProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShortUserAndRoomDto> kafkaListenerContainerFactoryAddUserToRoom() {
        ConcurrentKafkaListenerContainerFactory<String, ShortUserAndRoomDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryAddUserToRoom());
        return factory;
    }
    // Shared props

    private Map<String, Object> generateProps() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    private Map<String, Object> generateConfigProps(String clientId) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        return configProps;
    }

}
