package com.example.apigateway;

import com.example.apigateway.feignClients.AuthenticationService;
import com.example.apigateway.feignClients.ChatService;
import com.example.apigateway.feignClients.UserManagementService;
import com.example.apigateway.service.UserService;
import com.xent.DTO.APIGateway.FullUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserService.class)
@ActiveProfiles("test")
class ApiGatewayApplicationTests {
	@MockBean
	private AuthenticationService authenticationService;
	@MockBean
	private UserManagementService userManagementService;
	@MockBean
	private ChatService chatService;
	@MockBean
	private KafkaTemplate<String, FullUserDto> kafkaTemplate;

	@Test
	void contextLoads() {

	}


}
