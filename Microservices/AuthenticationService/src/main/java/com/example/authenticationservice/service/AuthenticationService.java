package com.example.authenticationservice.service;

import com.example.authenticationservice.DTO.RoleDto;
import com.example.authenticationservice.DTO.UserCredentialsDto;
import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.domain.User;
import com.example.authenticationservice.repository.AuthRepository;

import com.example.authenticationservice.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class AuthenticationService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;

    public AuthenticationService(AuthRepository authRepository, RoleRepository roleRepository) {
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username does not exist");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();



        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

    @GetMapping("/api/index")
    public String index() {
        return "Welcome to the authentication controller API";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return authRepository.findAll();
    }

    @PostMapping("/register")
    public void register(@RequestBody UserCredentialsDto user) {
        User savedUser = new User(user.getNickname(), user.getPassword());
        for (RoleDto roleDto: user.getRoles()) {
            Role role = roleRepository.findRoleByName(roleDto.getName());
            if (role == null) {
                return; // TODO
            }
            savedUser.addRole(role);
        }
        authRepository.save(savedUser);
    }


}
