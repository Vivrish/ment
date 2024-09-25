package com.example.e2etests;

import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import com.xent.DTO.ChatService.ShortMessageDto;
import com.xent.DTO.ChatService.ShortRoomDto;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;


import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;


import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.awaitility.Awaitility.await;
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
    public void incorrectLogin() throws InterruptedException {
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
        Thread.sleep(10000); // Wait until server processes the registration
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
        pollUser(userToRegisterCredentials);
        log.debug("User {} is registered successfully", userToRegister);

        String authHeader = loginUser(userToRegisterCredentials);

        ValidatableResponse protectedInfoResponse = getUser("bil", authHeader);
        protectedInfoResponse.body("username", equalTo(userToRegister.getUsername()));
        log.debug("Correct login scenario is complete");
    }

    @Test
    public void partialFailure() throws InterruptedException {
        log.debug("Starting partial failure scenario");
        FullUserDto userToRegister = new FullUserDto("ant", "pwd", null, "Banderas", "actor");
        UserCredentialsDto userToRegisterCredentials = new UserCredentialsDto(userToRegister);
        log.debug("Trying to register a user with null first name: {}", userToRegister);
        registerUser(userToRegister);
        log.debug("Server has accepted the request with null first name");
        Thread.sleep(10000);
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
    public void messagingInOneRoom() throws InterruptedException {
        log.debug("Starting messagingInOneRoom scenario");
        FullUserDto carl = new FullUserDto("carl", "pwd", "Carl", "Coral", "Animal");
        FullUserDto clara = new FullUserDto("clara", "pwd", "Clara", "Clarinet", "Music");
        ShortRoomDto room = new ShortRoomDto("carlAndClara");
        ShortMessageDto carlMessage = new ShortMessageDto("Hey Clara", "carlAndClara", "carl");
        ShortMessageDto claraMessage = new ShortMessageDto("Hey Carl", "carlAndClara", "clara");

        registerUser(carl);
        registerUser(clara);

        pollUser(new UserCredentialsDto(carl));
        pollUser(new UserCredentialsDto(clara));

        String claraToken = loginUser(new UserCredentialsDto(clara));
        String carlToken = loginUser(new UserCredentialsDto(carl));

        makeRoom(room, carlToken);

        Thread.sleep(10000);   // Wait until server creates the room

        addUserToRoom(clara.getUsername(), room.getName(), claraToken);
        addUserToRoom(carl.getUsername(), room.getName(), carlToken);

        Thread.sleep(10000);   // Wait until server adds users to the room

        sendMessage(carlMessage, carlToken);
        sendMessage(claraMessage, claraToken);

        Thread.sleep(10000);   // Wait until server sends the messages

        ValidatableResponse serverSideCarl = getUser(carl.getUsername(), carlToken);
        ValidatableResponse serverSideClara = getUser(clara.getUsername(), claraToken);

        FullUserDto serverSideMappedCarl = serverSideCarl.extract().as(FullUserDto.class);
        FullUserDto serverSideMappedClara = serverSideClara.extract().as(FullUserDto.class);
        log.debug("Mapped carl and clara: {} {}", serverSideMappedCarl, serverSideMappedClara);
        assertTrue(serverSideMappedCarl.isEquivalent(carl));
        assertTrue(serverSideMappedClara.isEquivalent(clara));

        assertTrue(serverSideMappedCarl.getRooms().contains(room));
        assertTrue(serverSideMappedClara.getRooms().contains(room));

        assertTrue(serverSideMappedCarl.getMessages().contains(carlMessage));
        assertTrue(serverSideMappedClara.getMessages().contains(claraMessage));

        Collection<String> serverSideRoomMessages = serverSideMappedCarl.getRooms().iterator().next().getMessages();

        assertTrue(serverSideRoomMessages.contains(claraMessage.getMessage()));
        assertTrue(serverSideRoomMessages.contains(carlMessage.getMessage()));

        log.debug("messagingInOneRoom scenario is complete");
    }

    @Test
    public void testWebSockets() throws ExecutionException, InterruptedException {
        log.debug("Beginning the websockets test");
        FullUserDto ann = new FullUserDto("ann", "pwd", "Anny", "Brown", "Unknown");
        UserCredentialsDto annCredentials = new UserCredentialsDto(ann);
        ShortRoomDto annRoom = new ShortRoomDto("annRoom");
        registerUser(ann);
        pollUser(annCredentials);
        String annAuth = loginUser(annCredentials);
        makeRoom(annRoom, annAuth);
        Thread.sleep(10000);
        addUserToRoom("ann", "annRoom", annAuth);
        Thread.sleep(10000);
        log.debug("Finished setting up data for testing websockets");


        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        StompSession session = stompClient.connectAsync("ws://localhost:8100/ws", new StompSessionHandlerAdapter() {
        }).get();
        log.debug("Session created");
        session.subscribe("/topic/room/annRoom", new StompFrameHandler() {
            @NonNull
            @Override
            public Type getPayloadType(@NonNull StompHeaders headers) {
                return ShortMessageDto.class;
            }

            @Override
            public void handleFrame(@NonNull StompHeaders headers, Object payload) {
                log.debug("Frame received: {}", payload);
            }
        });
        log.debug("Subscribe to the room topic");

        ShortMessageDto message = new ShortMessageDto("msg", "annRoom", "ann");
        session.send("/app/sendMessage", message);
        log.debug("Websocket message is sent");
        Thread.sleep(10000);

        FullUserDto serverSideAnn = getUser(ann.getUsername(), annAuth).extract().as(FullUserDto.class);
        log.debug("Got server side ann: {}", serverSideAnn);

        assertTrue(serverSideAnn.getRooms().contains(annRoom));
        assertEquals(serverSideAnn.getMessages().size(), 1);
        ShortMessageDto serverSideMessage = serverSideAnn.getMessages().iterator().next();
        assertEquals(serverSideMessage.getMessage(), "msg");
        assertEquals(serverSideAnn.getRooms().size(), 1);
        ShortRoomDto serverSideRoom = serverSideAnn.getRooms().iterator().next();
        assertTrue(serverSideRoom.getMessages().contains("msg"));
        log.debug("Finished testing websockets");
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

    private void addUserToRoom(String username, String roomName, String auth) {
        given()
                .header("Authorization", auth)
                .post("/rooms/%s/%s".formatted(roomName, username))
                .then()
                .statusCode(202);
    }

    private boolean isUserRegistered(UserCredentialsDto userCredentialsDto) {
        try {
            loginUser(userCredentialsDto);
            log.debug("Successfully polled a user {}", userCredentialsDto.getUsername());
            return true;
        }
        catch (Exception e) {
            log.debug("Unable to get a user {} from server during a poll", userCredentialsDto.getUsername());
            return false;
        }
    }

    private void pollUser(UserCredentialsDto userToPoll) {
        log.debug("Polling user {}", userToPoll);
        await().atMost(10, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).until(() -> isUserRegistered(userToPoll));
    }

}
