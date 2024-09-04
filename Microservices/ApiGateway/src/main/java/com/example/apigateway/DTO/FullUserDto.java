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
    private String firstName;
    private String lastName;
    private String description;
    private Collection<RoleDto> roles = new ArrayList<>();
    private Collection<ShortUserDto> contacts = new ArrayList<>();



    @Override
    public String toString() {
        return "FullUserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
