package com.example.e2etests.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class FullUserCredentialsDto {
    private String nickname;
    private String password;
    private Collection<RoleDto> roles = new ArrayList<>();

    public  FullUserCredentialsDto(FullUserDto fullUserDto) {
        this.nickname = fullUserDto.getUsername();
        this.password = fullUserDto.getPassword();
        this.roles = fullUserDto.getRoles();
    }

    @Override
    public String toString() {
        return "FullUserCredentialsDto{" +
                "nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
