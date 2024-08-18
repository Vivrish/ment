package com.example.authenticationservice.controller;

import com.example.authenticationservice.DTO.UserCredentialsDto;
import com.example.authenticationservice.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/users")
    List<UserCredentialsDto> getUsers() {
        return authenticationService.getAllUsers();
    }

    @PostMapping("/register")
    public void register(@RequestBody UserCredentialsDto user) {
        authenticationService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserCredentialsDto user) {
        return authenticationService.login(user);
    }
}
