package com.example.apigateway.controller;



import com.example.apigateway.DTO.FullUserDto;
import com.example.apigateway.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public void register(@RequestBody FullUserDto userDto) {
        userService.addUser(userDto);
    }

}
