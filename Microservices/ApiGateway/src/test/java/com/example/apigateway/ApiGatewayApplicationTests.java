package com.example.apigateway;

import com.xent.DTO.APIGateway.FullUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ApiGatewayApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	void fullUserEqual() {
		FullUserDto user = new FullUserDto("bob","password", "Bob", "Dylan", "desc");
		FullUserDto userTheSame = new FullUserDto("bob","password", "Bob", "Dylan", "desc");
		FullUserDto userWithTheSameUsername = new FullUserDto("bob","pwd", "Robert", "Jordan", "foo");
		FullUserDto userWithDifferentUsername = new FullUserDto("rob","password", "Bob", "Dylan", "desc");
        assertEquals(user, userTheSame);
		assertTrue(user.isEquivalent(userTheSame));

		assertEquals(user, userWithTheSameUsername);
		assertFalse(user.isEquivalent(userWithTheSameUsername));

		assertNotEquals(user, userWithDifferentUsername);
		assertFalse(user.isEquivalent(userWithDifferentUsername));
	}


}
