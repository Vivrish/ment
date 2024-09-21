package com.example.apigateway;

import com.example.apigateway.feignClients.AuthenticationService;
import com.example.apigateway.feignClients.ChatService;
import com.example.apigateway.feignClients.UserManagementService;
import com.example.apigateway.service.UserService;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import com.xent.DTO.ChatService.FullChatUserDto;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = UserService.class)
@ActiveProfiles("integrationTest")
class ApiGatewayIntegrationTests {
	@MockBean
	private AuthenticationService authenticationService;
	@MockBean
	private UserManagementService userManagementService;
	@MockBean
	private ChatService chatService;
	@MockBean
	private KafkaTemplate<String, FullUserDto> kafkaTemplate;
	@Autowired
	private UserService userService;


	@Test
	void contextLoads() {

	}

	@Test
	void correctUserAssemble() {
		UserCredentialsDto authServiceOutput = new UserCredentialsDto("ant", "password");
		FullUserDetailsDto managementServiceOutput = new FullUserDetailsDto();
		managementServiceOutput.setUsername("ant");
		managementServiceOutput.setFirstName("Antonio");
		managementServiceOutput.setLastName("Banderas");
		managementServiceOutput.setDescription("Actor");
		FullChatUserDto chatServiceOutput = new FullChatUserDto();
		chatServiceOutput.setUsername("ant");

		Mockito.when(authenticationService.getUserByName("ant")).thenReturn(authServiceOutput);
		Mockito.when(userManagementService.getUserByName("ant")).thenReturn(managementServiceOutput);
		Mockito.when(chatService.getUserByName("ant")).thenReturn(chatServiceOutput);

		FullUserDto assembledUser = userService.getUserByName("ant");
		assertEquals(assembledUser.getUsername(), "ant");
		assertEquals(assembledUser.getFirstName(), "Antonio");
		assertEquals(assembledUser.getLastName(), "Banderas");
		assertEquals(assembledUser.getDescription(), "Actor");
		assertEquals(assembledUser.getPassword(), "password");
	}





}
