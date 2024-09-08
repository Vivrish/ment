package com.example.chatservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ChatServiceApplicationTests {
    @MockBean
    KafkaAdmin kafkaAdmin;
    @Test
    void contextLoads() {
    }

}
