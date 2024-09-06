package com.example.apigateway.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class FullUserDetailsDto {
    private String nickname;
    private String firstName;
    private String lastName;
    private String description;
    private SettingsDto settings;
    private Collection<ShortUserDetailsDto> contacts;


    public FullUserDetailsDto(FullUserDto fullUserDto) {
        this.nickname = fullUserDto.getUsername();
        this.firstName = fullUserDto.getFirstName();
        this.lastName = fullUserDto.getLastName();
        this.description = fullUserDto.getDescription();
        this.contacts = fullUserDto.getContacts();
        this.settings = fullUserDto.getSettings();
    }
}
