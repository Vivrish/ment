package com.example.chatservice.service;

import com.example.chatservice.DTO.FullUserDto;
import com.example.chatservice.DTO.ShortUserDto;
import com.example.chatservice.domain.UserEntity;
import com.example.chatservice.exception.UserDoesNotExistException;
import com.example.chatservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public FullUserDto getUserByNickname(String nickname) {
        UserEntity user = getUserOrThrow(nickname);
        return new FullUserDto(user);
    }
    public FullUserDto addUser(ShortUserDto userToAdd) {
        UserEntity userEntity = new UserEntity(userToAdd);
        return new FullUserDto(userRepository.save(userEntity));
    }

    public FullUserDto editUser(String username, ShortUserDto userToEdit) {
        UserEntity userEntity = getUserOrThrow(username);
        userEntity.setFields(userToEdit);
        return new FullUserDto(userRepository.save(userEntity));
    }

    public void deleteUser(String username) {
        if (!userRepository.existsByNickname(username)) {
            throw new UserDoesNotExistException();
        }
        userRepository.deleteByNickname(username);
    }


    private UserEntity getUserOrThrow(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(UserDoesNotExistException::new);
    }


}
