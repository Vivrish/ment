package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.service.SettingsService;
import com.example.usermanagementservice.service.UserService;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
import com.xent.DTO.UserManagementService.SettingsDto;
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
    public Collection<FullUserDetailsDto> getUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{nickname}")
    public FullUserDetailsDto getUserByNickname(@PathVariable String nickname) {
        return userService.getUserByNickname(nickname);
    }
    @GetMapping("/{nickname}/settings")
    public SettingsDto getSettings(@PathVariable String nickname) {
        return settingsService.getSettingsByUsername(nickname);
    }
    @PostMapping("")
    public FullUserDetailsDto createUser(@RequestBody FullUserDetailsDto user) {
        return userService.createUser(user);
    }
    @PutMapping("")
    public FullUserDetailsDto editUser(FullUserDetailsDto user) {
        return userService.editUserByNickName(user);
    }
    @PutMapping("/{nickname}/settings")
    public SettingsDto editSettingsByUsername(@PathVariable String nickname, @RequestBody SettingsDto settingsDto) {
        return settingsService.editSettingsByUsername(nickname, settingsDto);
    }


}
