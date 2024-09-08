package com.example.apigateway;


import com.example.apigateway.DTO.FullChatUserDto;
import com.example.apigateway.DTO.FullUserCredentialsDto;
import com.example.apigateway.DTO.FullUserDetailsDto;
import com.example.apigateway.DTO.FullUserDto;
import com.example.apigateway.feignClients.AuthenticationService;
import com.example.apigateway.feignClients.ChatService;
import com.example.apigateway.feignClients.UserManagementService;
import com.example.apigateway.service.UserService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("main")
@AllArgsConstructor
@Slf4j
public class ApiGatewayE2eTests {
    private final AuthenticationService authenticationService;
    private final UserManagementService userManagementService;
    private final ChatService chatService;
    private final UserService userService;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1";
    }

    @Test
    public void eurekaIntact() {
        assertFalse(authenticationService.authenticate("DUMMY_TOKEN"));
        assertThrows(ResponseStatusException.class, () -> userManagementService.getUserByName("NON_EXISTENT_USER"));
        assertThrows(ResponseStatusException.class, () -> chatService.getUserByName("NON_EXISTENT_USER"));
    }

    @Test
    public void kafkaIntact() {
        FullUserCredentialsDto credentials = null;
        FullUserDetailsDto userDetails = null;
        FullChatUserDto chatUser = null;
        String username = "ant";

        FullUserDto user = new FullUserDto(username, "pwd", "Antonio", "Banderas", "Actor");

        assertDoesNotThrow(() -> userService.addUser(user));

        try {
            credentials = authenticationService.getUserByName(username);
            userDetails = userManagementService.getUserByName(username);
            chatUser = chatService.getUserByName(username);
        }
        catch (Exception e) {
            fail("getUserByName threw an exception: " + e.getMessage());
        }
        assertNotNull(credentials);
        assertNotNull(userDetails);
        assertNotNull(chatUser);
        assertEquals(credentials.getNickname(), user.getUsername());
        assertEquals(userDetails.getNickname(), user.getUsername());
        assertEquals(chatUser.getNickname(), user.getUsername());
    }

    @Test
    public void correctLogin() {
        FullUserDto userToRegister = new FullUserDto("bil", "password", "billy", "bones", "pirate");
        FullUserCredentialsDto userToRegisterCredentials = new FullUserCredentialsDto(userToRegister);
        given()
                .when()
                .contentType("application/json")
                .body(userToRegister)
                .post("/users")
                .then()
                .statusCode(200);

        FullUserCredentialsDto credentials = null;
        try {
            credentials = authenticationService.getUserByName("bil");
        }
        catch (Exception e) {
            fail("getUserByName threw an exception: " + e.getMessage());
        }
        assertNotNull(credentials);
        assertEquals(credentials.getNickname(), userToRegister.getUsername());

        given()
                .when()
                .get("/users/bil")
                .then()
                .statusCode(401);

        ValidatableResponse loginResponse = given()
                .when()
                .contentType("application/json")
                .body(userToRegisterCredentials)
                .post("/users/login")
                .then()
                .statusCode(200);

        String jwtToken = loginResponse.extract().asString();
        log.info("Extracted token: {}", jwtToken);

        ValidatableResponse protectedInfoResponse = given()
                .header("Authorization", "Bearer " + jwtToken)
                .get("/users/bil")
                .then()
                .statusCode(200);

        protectedInfoResponse.body("username", equalTo(userToRegister.getUsername()));
    }





}
