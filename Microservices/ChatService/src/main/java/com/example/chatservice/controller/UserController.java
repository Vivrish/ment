package com.example.chatservice.controller;

import com.example.chatservice.service.UserService;
import com.xent.DTO.ChatService.FullChatUserDto;
import com.xent.DTO.ChatService.ShortChatUserDto;
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
    FullChatUserDto createUser(@RequestBody ShortChatUserDto shortUserDto) {
        return userService.addUser(shortUserDto);
    }

    @PutMapping("/{username}")
    FullChatUserDto editUser(@RequestBody ShortChatUserDto shortUserDto, @PathVariable String username) {
        return userService.editUser(username, shortUserDto);
    }
    @DeleteMapping("/{username}")
    void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

}
