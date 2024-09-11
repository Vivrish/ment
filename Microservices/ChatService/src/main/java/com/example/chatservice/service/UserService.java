package com.example.chatservice.service;

import com.example.chatservice.DTO.FullChatUserDto;
import com.example.chatservice.DTO.ShortUserDto;
import com.example.chatservice.domain.UserEntity;
import com.example.chatservice.exception.UserDoesNotExistException;
import com.example.chatservice.repository.UserRepository;
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

    public FullChatUserDto getUserByNickname(String nickname) {
        UserEntity user = getUserOrThrow(nickname);
        return new FullChatUserDto(user);
    }
    public FullChatUserDto addUser(ShortUserDto userToAdd) {
        UserEntity userEntity = new UserEntity(userToAdd);
        log.debug("Adding new user: {}",userToAdd);
        return new FullChatUserDto(userRepository.save(userEntity));
    }

    public FullChatUserDto editUser(String username, ShortUserDto userToEdit) {
        UserEntity userEntity = getUserOrThrow(username);
        userEntity.setFields(userToEdit);
        return new FullChatUserDto(userRepository.save(userEntity));
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
