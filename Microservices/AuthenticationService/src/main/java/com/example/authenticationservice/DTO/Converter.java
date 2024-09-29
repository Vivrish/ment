package com.example.authenticationservice.DTO;

import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.domain.User;
import com.xent.DTO.AuthenticationService.RoleDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public UserCredentialsDto userCredentialsDto(User user) {
        UserCredentialsDto userCredentials = new UserCredentialsDto();
        userCredentials.setUsername(user.getName());
        userCredentials.setPassword(user.getPassword());
        for (Role role: user.getRoles()) {
            userCredentials.addRole(roleDto(role));
        }
        return userCredentials;
    }

    public RoleDto roleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(roleDto.getName());
        return roleDto;
    }

}
