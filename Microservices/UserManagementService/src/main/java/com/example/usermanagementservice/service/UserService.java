package com.example.usermanagementservice.service;

import com.example.usermanagementservice.DTO.FullUserDto;
import com.example.usermanagementservice.domain.Settings;
import com.example.usermanagementservice.domain.User;
import com.example.usermanagementservice.exception.UserDoesNotExistException;
import com.example.usermanagementservice.repository.SettingsRepository;
import com.example.usermanagementservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final SettingsRepository settingsRepository;

    public Collection<FullUserDto> getAllUsers() {
        Collection<FullUserDto> users = new ArrayList<>();
        for (User user: userRepository.findAll()) {
            users.add(new FullUserDto(user));
        }
        return users;
    }

    public FullUserDto getUserByNickname(String nickname) throws UserDoesNotExistException{
        User user = userRepository.getByNickname(nickname);
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        return new FullUserDto(user);
    }

    public FullUserDto editUserByNickName(@NonNull FullUserDto updatedUser) throws UserDoesNotExistException {
        User user = userRepository.getByNickname(updatedUser.getNickname());
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        user.updateAttributes(updatedUser);
        return new FullUserDto(userRepository.save(user));
    }

    public FullUserDto createUser(FullUserDto fullUserDto) {
        log.debug("Registering user {}", fullUserDto);
        User user = new User(fullUserDto);
        Settings settings = new Settings();
        if (fullUserDto.getSettings() != null) {
            settings.updateAttributes(fullUserDto.getSettings());
        }
        settingsRepository.save(settings);
        user.setSettings(settings);
        return new FullUserDto(userRepository.save(user));
    }
}
