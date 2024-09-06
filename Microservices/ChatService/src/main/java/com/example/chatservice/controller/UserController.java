package com.example.chatservice.controller;

import com.example.chatservice.DTO.FullUserDto;
import com.example.chatservice.DTO.ShortUserDto;
import com.example.chatservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    FullUserDto getByUsername(@PathVariable String username) {
        return userService.getUserByNickname(username);
    }

    @PostMapping("")
    FullUserDto createUser(@RequestBody ShortUserDto shortUserDto) {
        return userService.addUser(shortUserDto);
    }

    @PutMapping("/{username}")
    FullUserDto editUser(@RequestBody ShortUserDto shortUserDto, @PathVariable String username) {
        return userService.editUser(username, shortUserDto);
    }
    @DeleteMapping("/{username}")
    void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

}
