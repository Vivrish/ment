package com.example.usermanagementservice.service;

import com.example.usermanagementservice.DTO.Converter;
import com.example.usermanagementservice.domain.Settings;
import com.example.usermanagementservice.domain.User;
import com.example.usermanagementservice.exception.UserDoesNotExistException;
import com.example.usermanagementservice.repository.UserRepository;
import com.xent.DTO.UserManagementService.SettingsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SettingsService {
    private final UserRepository userRepository;
    private final Converter converter;

    public SettingsDto getSettingsByUsername(String username) throws UserDoesNotExistException{
        User user = userRepository.getByNickname(username);
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        return converter.settingsDto(user.getSettings());
    }

    public SettingsDto editSettingsByUsername(String username, SettingsDto settingsDto) {
        User user = userRepository.getByNickname(username);
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        Settings settings = user.getSettings();
        settings.updateAttributes(settingsDto);
        user.setSettings(settings);
        return converter.settingsDto(userRepository.save(user).getSettings());
    }
}
