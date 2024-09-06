package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.DTO.SettingsDto;
import com.example.usermanagementservice.DTO.FullUserDto;
import com.example.usermanagementservice.service.ContactService;
import com.example.usermanagementservice.service.SettingsService;
import com.example.usermanagementservice.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final SettingsService settingsService;

    @GetMapping("")
    public Collection<FullUserDto> getUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{nickname}")
    public FullUserDto getUserByNickname(@PathVariable String nickname) {
        return userService.getUserByNickname(nickname);
    }
    @GetMapping("/{nickname}/settings")
    public SettingsDto getSettings(@PathVariable String nickname) {
        return settingsService.getSettingsByUsername(nickname);
    }
    @PostMapping("")
    public FullUserDto createUser(@RequestBody FullUserDto user) {
        return userService.createUser(user);
    }
    @PutMapping("")
    public FullUserDto editUser(FullUserDto user) {
        return userService.editUserByNickName(user);
    }
    @PutMapping("/{nickname}/settings")
    public SettingsDto editSettingsByUsername(@PathVariable String nickname, @RequestBody SettingsDto settingsDto) {
        return settingsService.editSettingsByUsername(nickname, settingsDto);
    }


}
