package com.example.apigateway.controller;


import com.example.apigateway.service.UserService;
import com.xent.DTO.APIGateway.FullUserDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public FullUserDto getUserByName(@PathVariable String username, @RequestHeader("Authorization") String authHeader ) {
        return userService.getUserByName(username, authHeader);
    }
    @PostMapping("")
    public void register(@RequestBody FullUserDto userDto) {
        userService.addUser(userDto);
    }
    @PostMapping("/login")
    public String login(@RequestBody UserCredentialsDto credentials) {
        return userService.login(credentials);
    }

}
