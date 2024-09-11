package com.example.apigateway.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class FullUserCredentialsDto {
    private String username;
    private String password;
    private Collection<RoleDto> roles = new ArrayList<>();

    public  FullUserCredentialsDto(FullUserDto fullUserDto) {
        this.username = fullUserDto.getUsername();
        this.password = fullUserDto.getPassword();
        this.roles = fullUserDto.getRoles();
    }

    @Override
    public String toString() {
        return "FullUserCredentialsDto{" +
                "nickname='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
