package com.example.apigateway.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullUserDetailsDto {
    private String nickname;
    private String firstName;
    private String lastName;
    private String description;

    public FullUserDetailsDto(FullUserDto fullUserDto) {
        this.nickname = fullUserDto.getUsername();
        this.firstName = fullUserDto.getFirstName();
        this.lastName = fullUserDto.getLastName();
        this.description = fullUserDto.getDescription();
    }
}
