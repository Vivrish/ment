package com.xent.DTO.APIGateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FullUserDto.class)
public class FullUserDtoTest {
    private final FullUserDto user = new FullUserDto("bob","password", "Bob", "Dylan", "desc");
    private final FullUserDto userTheSame = new FullUserDto("bob","password", "Bob", "Dylan", "desc");
    private final FullUserDto userWithTheSameUsername = new FullUserDto("bob","pwd", "Robert", "Jordan", "foo");
    private final FullUserDto userWithDifferentUsername = new FullUserDto("rob","password", "Bob", "Dylan", "desc");


    @Test
    public void contextLoads() {

    }

    @Test
    void fullUserEqual() {
        assertEquals(user, userTheSame);
        assertEquals(user, userWithTheSameUsername);
        assertNotEquals(user, userWithDifferentUsername);
    }

    @Test
    void fullUserEquivalent() {
        assertTrue(user.isEquivalent(userTheSame));
        assertFalse(user.isEquivalent(userWithTheSameUsername));
        assertFalse(user.isEquivalent(userWithDifferentUsername));
    }
}
