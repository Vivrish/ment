package com.example.authenticationservice.DTO;

import com.example.authenticationservice.domain.Role;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RoleDto {
    private String name;

    public RoleDto(String name) {
        this.name = name;
    }

    public RoleDto(Role role) {
        this.name = role.getName();
    }
}
