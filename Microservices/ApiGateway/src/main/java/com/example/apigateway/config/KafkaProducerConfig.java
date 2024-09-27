package com.example.apigateway.config;

import com.xent.DTO.APIGateway.FailureDto;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.APIGateway.ShortUserAndRoomDto;
import com.xent.DTO.ChatService.ShortMessageDto;
import com.xent.DTO.ChatService.ShortRoomDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS_CONFIG;
    @Bean
    public ProducerFactory<String, FullUserDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(generateConfigProps("FullUserDto"));
    }

    @Bean
    public KafkaTemplate<String, FullUserDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, FailureDto> producerFactoryFailure() {
        return new DefaultKafkaProducerFactory<>(generateConfigProps("FailureDto"));
    }

    @Bean
    public KafkaTemplate<String, FailureDto> kafkaTemplateFailure() {
        return new KafkaTemplate<>(producerFactoryFailure());
    }

    @Bean
    public ProducerFactory<String, ShortRoomDto> producerFactoryNewRoom() {
        return new DefaultKafkaProducerFactory<>(generateConfigProps("ShortRoomDto"));
    }

    @Bean
    public KafkaTemplate<String, ShortRoomDto> kafkaTemplateNewRoom() {
        return new KafkaTemplate<>(producerFactoryNewRoom());
    }

    @Bean
    public ProducerFactory<String, ShortMessageDto> producerFactoryMessageHttp() {
        return new DefaultKafkaProducerFactory<>(generateConfigProps("ShortMessageDto"));
    }

    @Bean
    public KafkaTemplate<String, ShortMessageDto> kafkaTemplateMessageHttp() {
        return new KafkaTemplate<>(producerFactoryMessageHttp());
    }

    @Bean
    public ProducerFactory<String, ShortUserAndRoomDto> producerFactoryAddUserToRoom() {
        return new DefaultKafkaProducerFactory<>(generateConfigProps("ShortUserAndRoomDto"));
    }

    @Bean
    public KafkaTemplate<String, ShortUserAndRoomDto> kafkaTemplateAddUserToRoom() {
        return new KafkaTemplate<>(producerFactoryAddUserToRoom());
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
