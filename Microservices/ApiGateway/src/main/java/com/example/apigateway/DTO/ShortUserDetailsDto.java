package com.example.apigateway.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class ShortUserDetailsDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String description;
    private Collection<RoleDto> roles = new ArrayList<>();
}
