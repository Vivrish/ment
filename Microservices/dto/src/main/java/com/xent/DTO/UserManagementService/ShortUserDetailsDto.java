package com.xent.DTO.UserManagementService;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class ShortUserDetailsDto {
    private String username;
    private String firstName;
    private String lastName;
    private String description;
    private Collection<ContactDto> contacts;
    private SettingsDto settings;

    public ShortUserDetailsDto(FullUserDetailsDto userDetailsDto) {
        this.username = userDetailsDto.getUsername();
        this.firstName = userDetailsDto.getFirstName();
        this.lastName = userDetailsDto.getLastName();
        this.description = userDetailsDto.getDescription();
        this.contacts = userDetailsDto.getContacts();
        this.settings = userDetailsDto.getSettings();
    }
}
