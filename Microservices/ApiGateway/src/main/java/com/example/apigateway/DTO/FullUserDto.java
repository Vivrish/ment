package com.example.apigateway.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class FullUserDto {
    private String username;
    private String password;
    private Collection<RoleDto> roles = new ArrayList<>();
}
