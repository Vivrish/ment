package com.example.usermanagementservice;

import com.example.usermanagementservice.DTO.Converter;
import com.example.usermanagementservice.domain.User;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Converter.class)
@ActiveProfiles("test")
class UserManagementServiceApplicationTests {
    Converter converter = new Converter();


    @Test
    void contextLoads() {
    }

    @Test
    void correctConversion() {
        User user = new User("bob", "Bob", "Dylan", "Man", null);
        FullUserDetailsDto convertedUser = converter.fullUserDetailsDto(user);
        assertEquals(convertedUser.getUsername(), user.getNickname());
        assertEquals(convertedUser.getFirstName(), user.getFirstName());
        assertEquals(convertedUser.getLastName(), user.getLastName());
        assertEquals(convertedUser.getDescription(), user.getDescription());
    }

}
