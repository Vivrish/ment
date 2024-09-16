package com.xent.DTO.AuthenticationService;


import com.xent.DTO.APIGateway.FullUserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class UserCredentialsDto {

    private String username;
    private String password;
    private Collection<RoleDto> roles;

    public UserCredentialsDto(FullUserDto fullUser) {
        this.username = fullUser.getUsername();
        this.password = fullUser.getPassword();
        this.roles = fullUser.getRoles();
    }

    public UserCredentialsDto(String nickname, String password, Collection<RoleDto> roles) {
        this.username = nickname;
        this.password = password;
        this.roles = roles;
    }


    public UserCredentialsDto(String nickname, String password) {
        this.username = nickname;
        this.password = password;
        this.roles = new ArrayList<>();
    }



    public void addRole(RoleDto roleDto) {
        roles.add(roleDto);
    }
}
