package com.example.chatservice.service;

import com.example.chatservice.DTO.Converter;
import com.example.chatservice.domain.UserEntity;
import com.example.chatservice.exception.UserDoesNotExistException;
import com.example.chatservice.repository.UserRepository;
import com.xent.DTO.ChatService.FullChatUserDto;
import com.xent.DTO.ChatService.ShortChatUserDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final Converter converter;

    public FullChatUserDto getUserByNickname(String nickname) {
        UserEntity user = getUserOrThrow(nickname);
        return converter.fullChatUserDto(user);
    }
    public FullChatUserDto addUser(ShortChatUserDto userToAdd) {
        UserEntity userEntity = new UserEntity(userToAdd);
        log.debug("Adding new user: {}",userToAdd);
        return converter.fullChatUserDto(userRepository.save(userEntity));
    }

    public FullChatUserDto editUser(String username, ShortChatUserDto userToEdit) {
        UserEntity userEntity = getUserOrThrow(username);
        userEntity.setFields(userToEdit);
        return converter.fullChatUserDto(userRepository.save(userEntity));
    }

    public void deleteUser(String username) {
        if (!userRepository.existsByNickname(username)) {
            throw new UserDoesNotExistException();
        }
        userRepository.deleteByNickname(username);
    }

    public void deleteUserIfExists(String username) {
        if (userRepository.existsByNickname(username)) {
            userRepository.deleteByNickname(username);
        }
    }


    private UserEntity getUserOrThrow(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(UserDoesNotExistException::new);
    }


}
