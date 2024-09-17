package com.xent.DTO.UserManagementService;



import com.xent.DTO.APIGateway.FullUserDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FullUserDetailsDto {
    private String username;
    private String firstName;
    private String lastName;
    private String description;
    private Collection<ContactDto> contacts = new ArrayList<>();
    private SettingsDto settings;

    public FullUserDetailsDto(FullUserDto fullUser) {
        this.username = fullUser.getUsername();
        this.firstName = fullUser.getFirstName();
        this.lastName = fullUser.getLastName();
        this.description = fullUser.getDescription();
        this.contacts = fullUser.getContacts();
        this.settings = fullUser.getSettings();
    }


    @Override
    public String toString() {
        return "FullUserDetailsDto{" +
                "nickname='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void addContact(ContactDto contactDto) {
        this.contacts.add(contactDto);
    }
    public void removeContact(ContactDto contactDto) {
        this.contacts.remove(contactDto);
    }
}
