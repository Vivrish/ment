package com.example.authenticationservice.DTO;


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


    public void addRole(RoleDto roleDto) {
        roles.add(roleDto);
    }
}
