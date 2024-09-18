package com.example.e2etests;

import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import com.xent.DTO.ChatService.ShortMessageDto;
import com.xent.DTO.ChatService.ShortRoomDto;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class E2ETestsApplicationTests {

    @BeforeAll
    public static void setUp() {
        log.debug("Setting up rest assured");
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1";
        log.debug("Rest assured is set up");
    }

    @Test
    void contextLoads() {
        log.info("Context loads test passed");
    }

    @Test
    public void incorrectLogin() {
        log.debug("Starting incorrect login scenario");
        FullUserDto userToRegister = new FullUserDto("bob", "password", "bobby", "bones", "pirate");
        UserCredentialsDto incorrectCredentials = new UserCredentialsDto(userToRegister);
        incorrectCredentials.setPassword("not_bobs_password");
        given()
                .when()
                .contentType("application/json")
                .body(userToRegister)
                .post("/users")
                .then()
                .statusCode(202);
        log.debug("User {} is registered successfully", userToRegister);
        given()
                .when()
                .contentType("application/json")
                .body(incorrectCredentials)
                .post("/users/login")
                .then()
                .statusCode(403);
        log.debug("Incorrect login scenario is complete");
    }

    @Test
    public void correctLogin() {
        log.debug("Starting correct login scenario");
        FullUserDto userToRegister = new FullUserDto("bil", "password", "billy", "bones", "pirate");
        UserCredentialsDto userToRegisterCredentials = new UserCredentialsDto(userToRegister);
        registerUser(userToRegister);

        log.debug("User {} is registered successfully", userToRegister);

        String authHeader = loginUser(userToRegisterCredentials);

        ValidatableResponse protectedInfoResponse = getUser("bil", authHeader);
        protectedInfoResponse.body("username", equalTo(userToRegister.getUsername()));
        log.debug("Correct login scenario is complete");
    }

    @Test
    public void partialFailure() {
        log.debug("Starting partial failure scenario");
        FullUserDto userToRegister = new FullUserDto("ant", "pwd", null, "Banderas", "actor");
        UserCredentialsDto userToRegisterCredentials = new UserCredentialsDto(userToRegister);
        log.debug("Trying to register a user with null first name: {}", userToRegister);
        registerUser(userToRegister);
        log.debug("Server has accepted the request with null first name");
        given()
                .when()
                .contentType("application/json")
                .body(userToRegisterCredentials)
                .post("/users/login")
                .then()
                .statusCode(404);
        log.debug("Partial failure scenario is complete");
    }


    @Test
    public void messagingInOneRoom() {
        log.debug("Starting messagingInOneRoom scenario");
        FullUserDto carl = new FullUserDto("carl", "pwd", "Carl", "Coral", "Animal");
        FullUserDto clara = new FullUserDto("clara", "pwd", "Clara", "Clarinet", "Music");
        ShortRoomDto room = new ShortRoomDto("carlAndClara", null, null, null, null);
        ShortMessageDto carlMessage = new ShortMessageDto("Hey Clara", null, "carlAndClara", "carl");
        ShortMessageDto claraMessage = new ShortMessageDto("Hey Carl", null, "carlAndClara", "clara");

        registerUser(carl);
        registerUser(clara);

        String claraToken = loginUser(new UserCredentialsDto(clara));
        String carlToken = loginUser(new UserCredentialsDto(carl));

        makeRoom(room, carlToken);

        sendMessage(carlMessage, carlToken);
        sendMessage(claraMessage, claraToken);


        ValidatableResponse serverSideCarl = getUser(carl.getUsername(), carlToken);
        ValidatableResponse serverSideClara = getUser(clara.getUsername(), claraToken);

        FullUserDto serverSideMappedCarl = serverSideCarl.extract().as(FullUserDto.class);
        FullUserDto serverSideMappedClara = serverSideClara.extract().as(FullUserDto.class);

        assertEquals(serverSideMappedCarl, carl);
        assertEquals(serverSideMappedClara, clara);
    }

    private String loginUser(UserCredentialsDto userToRegisterCredentials) {
        ValidatableResponse loginResponse = given()
                .when()
                .contentType("application/json")
                .body(userToRegisterCredentials)
                .post("/users/login")
                .then()
                .statusCode(200);

        log.debug("User {} is logged in successfully", userToRegisterCredentials);
        String jwtToken = loginResponse.extract().asString();
        log.info("Extracted token: {}", jwtToken);
        return "Bearer " + jwtToken;
    }

    private void registerUser(FullUserDto userToRegister) {
        given()
                .when()
                .contentType("application/json")
                .body(userToRegister)
                .post("/users")
                .then()
                .statusCode(202);
    }

    private void makeRoom(ShortRoomDto room, String authHeader) {
        given()
                .header("Authorization", authHeader)
                .contentType("application/json")
                .body(room)
                .post("/rooms")
                .then()
                .statusCode(202);
    }
    private void sendMessage(ShortMessageDto message, String authHeader) {
        given()
                .header("Authorization", authHeader)
                .contentType("application/json")
                .body(message)
                .post("/messages")
                .then()
                .statusCode(202);
    }

    private ValidatableResponse getUser(String username, String authHeader) {
        return given()
                .header("Authorization", authHeader)
                .get("/users/%s".formatted(username))
                .then()
                .statusCode(200);
    }

}
