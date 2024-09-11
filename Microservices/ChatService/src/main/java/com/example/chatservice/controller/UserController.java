package com.example.chatservice.controller;

import com.example.chatservice.DTO.FullChatUserDto;
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
    FullChatUserDto getByUsername(@PathVariable String username) {
        return userService.getUserByNickname(username);
    }

    @PostMapping("")
    FullChatUserDto createUser(@RequestBody ShortUserDto shortUserDto) {
        return userService.addUser(shortUserDto);
    }

    @PutMapping("/{username}")
    FullChatUserDto editUser(@RequestBody ShortUserDto shortUserDto, @PathVariable String username) {
        return userService.editUser(username, shortUserDto);
    }
    @DeleteMapping("/{username}")
    void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

}
