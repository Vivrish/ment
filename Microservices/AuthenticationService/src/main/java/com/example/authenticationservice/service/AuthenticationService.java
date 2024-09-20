package com.example.authenticationservice.service;

import com.example.authenticationservice.DTO.Converter;
import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.domain.User;
import com.example.authenticationservice.exception.IncorrectCredentialsException;
import com.example.authenticationservice.exception.NameAlreadyExistsException;
import com.example.authenticationservice.exception.NameDoesNotExistException;
import com.example.authenticationservice.exception.RoleDoesNotExistException;
import com.example.authenticationservice.repository.AuthRepository;
import com.example.authenticationservice.repository.RoleRepository;
import com.xent.DTO.AuthenticationService.RoleDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AuthenticationService {
    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final Converter converter;


    public List<UserCredentialsDto> getAllUsers() {
        List<UserCredentialsDto> userCredentialsDtos = new ArrayList<>();
        for (User user: authRepository.findAll()) {
            userCredentialsDtos.add(converter.userCredentialsDto(user));
        }
        log.info("Getting all users");
        return userCredentialsDtos;
    }

    public void register(UserCredentialsDto user) throws RoleDoesNotExistException, NameDoesNotExistException {
        log.info("Registering user {}", user.getUsername());
        User savedUser = new User(user.getUsername(), encoder.encode(user.getPassword()));
        if (authRepository.existsByName(user.getUsername())) {
            throw new NameAlreadyExistsException();
        }
        for (RoleDto roleDto: user.getRoles()) {
            Role role = roleRepository.findRoleByName(roleDto.getName());
            if (role == null) {
                log.error("Role does not exist: {}", roleDto.getName());
                throw new RoleDoesNotExistException();
            }
            savedUser.addRole(role);
        }
        authRepository.save(savedUser);
        log.info("Registered user {}", user.getUsername());
    }

    public String login(UserCredentialsDto user) throws IncorrectCredentialsException, NameDoesNotExistException {
        log.info("Login attempt by {}", user.getUsername());
        if (!authRepository.existsByName(user.getUsername())) {
            throw new NameDoesNotExistException();
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (!authentication.isAuthenticated()) {
            log.error("Bad credentials for user {}", user.getUsername());
            throw new IncorrectCredentialsException();
        }
        log.info("User {} is logged in", user.getUsername());
        return jwtService.generateToken(user.getUsername());
    }


    public UserCredentialsDto getUserByName(String username) {
        log.info("Getting user credentials of {}", username);
        User user = authRepository.findUserByName(username)
                .orElseThrow(NameDoesNotExistException::new);
        return converter.userCredentialsDto(user);
    }

    public void deleteUserIfExists(String username) {
        log.debug("Deleting user: {}", username);
        if (authRepository.existsByName(username)) {
            authRepository.deleteByName(username);
        }
    }
}
