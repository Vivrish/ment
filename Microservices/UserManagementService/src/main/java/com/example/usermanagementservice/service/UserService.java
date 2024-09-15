package com.example.usermanagementservice.service;

import com.example.usermanagementservice.DTO.Converter;
import com.example.usermanagementservice.domain.Settings;
import com.example.usermanagementservice.domain.User;
import com.example.usermanagementservice.exception.UserDoesNotExistException;
import com.example.usermanagementservice.repository.SettingsRepository;
import com.example.usermanagementservice.repository.UserRepository;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final SettingsRepository settingsRepository;
    private final Converter converter;

    public Collection<FullUserDetailsDto> getAllUsers() {
        Collection<FullUserDetailsDto> users = new ArrayList<>();
        for (User user: userRepository.findAll()) {
            users.add(converter.fullUserDetailsDto(user));
        }
        return users;
    }

    public FullUserDetailsDto getUserByNickname(String nickname) throws UserDoesNotExistException{
        User user = userRepository.getByNickname(nickname);
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        return converter.fullUserDetailsDto(user);
    }

    public FullUserDetailsDto editUserByNickName(@NonNull FullUserDetailsDto updatedUser) throws UserDoesNotExistException {
        User user = userRepository.getByNickname(updatedUser.getUsername());
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        user.updateAttributes(updatedUser);
        return converter.fullUserDetailsDto(userRepository.save(user));
    }

    public FullUserDetailsDto createUser(FullUserDetailsDto fullUserDto) {
        log.debug("Registering user {}", fullUserDto);
        User user = new User(fullUserDto);
        Settings settings = new Settings();
        if (fullUserDto.getSettings() != null) {
            settings.updateAttributes(fullUserDto.getSettings());
        }
        settingsRepository.save(settings);
        user.setSettings(settings);
        return converter.fullUserDetailsDto(userRepository.save(user));
    }
}
