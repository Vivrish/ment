package com.example.authenticationservice.service;

import com.example.authenticationservice.DTO.UserCredentialsDto;
import com.example.authenticationservice.domain.User;
import com.example.authenticationservice.repository.AuthRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;
import java.util.List;


@RestController
public class AuthenticationService {

    private final AuthRepository authRepository;

    public AuthenticationService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @GetMapping("/api/index")
    public String index() {
        return "Welcome to the authentication controller API";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return authRepository.findAll();
    }

    @PostMapping("/register")
    public void register(@RequestBody UserCredentialsDto user) {

        User savedUser = new User(user.getNickname(), user.getPassword());
        System.out.println("-------------------------------------");
        System.out.println(savedUser.getName());
        authRepository.save(savedUser);
    }
}
