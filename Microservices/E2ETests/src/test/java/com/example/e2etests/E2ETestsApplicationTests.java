package com.example.e2etests;

import com.example.e2etests.DTO.FullUserCredentialsDto;
import com.example.e2etests.DTO.FullUserDto;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@Slf4j
class E2ETestsApplicationTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://APIGateway";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1";
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void incorrectLogin() {
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
        given()
                .when()
                .contentType("application/json")
                .body(incorrectCredentials)
                .post("/users/login")
                .then()
                .statusCode(403);
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
