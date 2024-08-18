package com.example.authenticationservice.service;

import com.example.authenticationservice.DTO.RoleDto;
import com.example.authenticationservice.DTO.UserCredentialsDto;
import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.domain.User;
import com.example.authenticationservice.exception.IncorrectCredentialsException;
import com.example.authenticationservice.exception.RoleDoesNotExistException;
import com.example.authenticationservice.repository.AuthRepository;

import com.example.authenticationservice.repository.RoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
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
        return userCredentialsDtos;
    }

    public void register(UserCredentialsDto user) throws RoleDoesNotExistException {
        User savedUser = new User(user.getNickname(), encoder.encode(user.getPassword()));
        for (RoleDto roleDto: user.getRoles()) {
            Role role = roleRepository.findRoleByName(roleDto.getName());
            if (role == null) {
                throw new RoleDoesNotExistException();
            }
            savedUser.addRole(role);
        }
        authRepository.save(savedUser);
    }

    public String login(UserCredentialsDto user) throws IncorrectCredentialsException {
        System.out.println("Login attempt");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getNickname(), user.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new IncorrectCredentialsException();
        }
        String token = jwtService.generateToken(user.getNickname());
        System.out.printf("Token generated: %s%n", token);
        return token;
    }


}
