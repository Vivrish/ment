package com.example.apigateway.controller;


import com.example.apigateway.service.AuthService;
import com.example.apigateway.service.UserService;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/{username}")
    public FullUserDto getUserByName(@PathVariable String username, @RequestHeader("Authorization") String authHeader ) {
        authService.authenticate(authHeader);
        return userService.getUserByName(username);
    }
    @PostMapping("")
    public ResponseEntity<Void> register(@RequestBody FullUserDto userDto) {
        userService.addUser(userDto);
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/login")
    public String login(@RequestBody UserCredentialsDto credentials) {
        return authService.login(credentials);
    }

}
