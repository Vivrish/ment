package com.example.authenticationservice.DTO;

import java.util.ArrayList;
import java.util.Collection;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Collection<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleDto> roles) {
        this.roles = roles;
    }
}
