package com.example.authenticationservice.DTO;


import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class UserCredentialsDto {

    private String nickname;
    private String password;
    private Collection<RoleDto> roles;

    public UserCredentialsDto(String nickname, String password, Collection<RoleDto> roles) {
        this.nickname = nickname;
        this.password = password;
        this.roles = roles;
    }


    public UserCredentialsDto(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public UserCredentialsDto(User user) {
        this.nickname = user.getName();
        this.password = user.getPassword();
        this.roles = new ArrayList<>();
        for (Role role: user.getRoles()) {
            roles.add(new RoleDto(role));
        }
    }


    public void addRole(RoleDto roleDto) {
        roles.add(roleDto);
    }
}
