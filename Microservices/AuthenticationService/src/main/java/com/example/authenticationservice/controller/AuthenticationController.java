package com.example.authenticationservice.controller;

import com.example.authenticationservice.DTO.UserCredentialsDto;
import com.example.authenticationservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Operation(summary = "Get all user credentials and their roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the users"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    @GetMapping("/users")
    List<UserCredentialsDto> getUsers() {
        return authenticationService.getAllUsers();
    }


    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration is successful"),
            @ApiResponse(responseCode = "403", description = "Name already exists or role does not exist"),
    })
    @PostMapping("/register")
    public void register(@RequestBody UserCredentialsDto user) {
        authenticationService.register(user);
    }

    @Operation(summary = "Login a user and get a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Incorrect username or password"),
    })
    @PostMapping("/login")
    public String login(@RequestBody UserCredentialsDto user) {
        return authenticationService.login(user);
    }
}
