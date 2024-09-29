package com.example.chatservice;

import com.example.chatservice.DTO.Converter;
import com.example.chatservice.domain.UserEntity;
import com.xent.DTO.ChatService.FullChatUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Converter.class)
@ActiveProfiles("test")
class ChatServiceApplicationTests {
    Converter converter = new Converter();
    @Test
    void contextLoads() {
    }
    @Test
    void correctConversion() {
        UserEntity user = new UserEntity();
        user.setNickname("bill");
        FullChatUserDto convertedUser = converter.fullChatUserDto(user);
        assertEquals(convertedUser.getUsername(), user.getNickname());
    }

}
