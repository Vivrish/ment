package com.example.apigateway.kafka;


import com.xent.DTO.APIGateway.FailureDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaListeners {
    @KafkaListener(topics = "register", groupId = "gatewayRegisterFailure")
    public void registerFailed(FailureDto failure) {
        log.error("Register failure detected: {}", failure);
    }
}
