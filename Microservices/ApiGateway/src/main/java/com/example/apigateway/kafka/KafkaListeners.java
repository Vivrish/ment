package com.example.apigateway.kafka;


import com.xent.DTO.APIGateway.FailureDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaListeners {
    private final KafkaTemplate<String, FailureDto> kafkaTemplate;

    @KafkaListener(topics = "register-failure", groupId = "gatewayRegisterFailure")
    public void registerFailed(FailureDto failure) {
        log.error("Register failure detected: {}", failure);
    }
}
