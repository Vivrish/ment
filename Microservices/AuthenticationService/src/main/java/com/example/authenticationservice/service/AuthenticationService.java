package com.example.authenticationservice.service;

import com.example.authenticationservice.DTO.RoleDto;
import com.example.authenticationservice.DTO.UserCredentialsDto;
import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.domain.User;
import com.example.authenticationservice.exception.IncorrectCredentialsException;
import com.example.authenticationservice.exception.NameAlreadyExistsException;
import com.example.authenticationservice.exception.RoleDoesNotExistException;
import com.example.authenticationservice.repository.AuthRepository;

import com.example.authenticationservice.repository.RoleRepository;
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
public class AuthenticationService {

    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(AuthRepository authRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }



    public List<UserCredentialsDto> getAllUsers() {
        List<UserCredentialsDto> userCredentialsDtos = new ArrayList<>();
        for (User user: authRepository.findAll()) {
            userCredentialsDtos.add(user.toUserCredentialsDto());
        }
        log.info("Getting all users");
        return userCredentialsDtos;
    }

    public void register(UserCredentialsDto user) throws RoleDoesNotExistException {
        log.info("Registering user {}", user.getNickname());
        User savedUser = new User(user.getNickname(), encoder.encode(user.getPassword()));
        if (authRepository.findUserByName(user.getNickname()) != null) {
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
        log.info("Registered user {}", user.getNickname());
    }

    public String login(UserCredentialsDto user) throws IncorrectCredentialsException {
        log.info("Login attempt by {}", user.getNickname());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getNickname(), user.getPassword()));
        if (!authentication.isAuthenticated()) {
            log.error("Bad credentials for user {}", user.getNickname());
            throw new IncorrectCredentialsException();
        }
        log.info("User {} is logged in", user.getNickname());
        return jwtService.generateToken(user.getNickname());
    }


}
