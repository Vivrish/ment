package com.example.e2etests;

import com.example.e2etests.DTO.FullUserCredentialsDto;
import com.example.e2etests.DTO.FullUserDto;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

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
        FullUserCredentialsDto incorrectCredentials = new FullUserCredentialsDto(userToRegister);
        incorrectCredentials.setPassword("not_bobs_password");
        given()
                .when()
                .contentType("application/json")
                .body(userToRegister)
                .post("/users")
                .then()
                .statusCode(200);
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
        FullUserCredentialsDto userToRegisterCredentials = new FullUserCredentialsDto(userToRegister);
        given()
                .when()
                .contentType("application/json")
                .body(userToRegister)
                .post("/users")
                .then()
                .statusCode(200);

        log.debug("User {} is registered successfully", userToRegister);

        ValidatableResponse loginResponse = given()
                .when()
                .contentType("application/json")
                .body(userToRegisterCredentials)
                .post("/users/login")
                .then()
                .statusCode(200);

        log.debug("User {} is logged in successfully", userToRegister);

        String jwtToken = loginResponse.extract().asString();
        log.info("Extracted token: {}", jwtToken);

        ValidatableResponse protectedInfoResponse = given()
                .header("Authorization", "Bearer " + jwtToken)
                .get("/users/bil")
                .then()
                .statusCode(200);

        protectedInfoResponse.body("username", equalTo(userToRegister.getUsername()));
        log.debug("Correct login scenario is complete");
    }

}
