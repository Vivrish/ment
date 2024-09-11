package com.example.authenticationservice.controller;

import com.example.authenticationservice.DTO.UserCredentialsDto;
import com.example.authenticationservice.service.AuthenticationService;
import com.example.authenticationservice.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;



    @Operation(summary = "Get all user credentials and their roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the users"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    @GetMapping("/users")
    List<UserCredentialsDto> getUsers() {
        return authenticationService.getAllUsers();
    }

    @GetMapping("/users/{username}")
    UserCredentialsDto getUserByName(@PathVariable String username) {
        return authenticationService.getUserByName(username);
    }


    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration is successful"),
            @ApiResponse(responseCode = "403", description = "Name already exists or role does not exist"),
    })
    @PostMapping("/register")
    public void register(@RequestBody @NonNull UserCredentialsDto user) {
        log.debug("Request accepted: register a user with attributes: {} {}",
                 user.getNickname(),
                 user.getPassword());
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

    @GetMapping("/auth/{token}")
    public boolean authenticate(@PathVariable String token) {
        log.debug("Request accepted: authenticate JWT {}", token);
        return jwtService.validateHeader(token);
    }
}
