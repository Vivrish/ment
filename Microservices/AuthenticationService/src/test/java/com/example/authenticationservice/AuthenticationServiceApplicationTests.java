package com.example.authenticationservice;

import com.example.authenticationservice.DTO.Converter;
import com.example.authenticationservice.domain.User;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = Converter.class)
@ActiveProfiles("test")
class AuthenticationServiceApplicationTests {
	Converter converter = new Converter();

	@Test
	void contextLoads() {
	}
	@Test
	void correctConversion() {
		User user = new User("ant", "password");
		UserCredentialsDto convertedUser = converter.userCredentialsDto(user);
		assertEquals(convertedUser.getUsername(), user.getName());
		assertEquals(convertedUser.getPassword(), user.getPassword());
	}

}
