package com.xent.DTO.UserManagementService;



import lombok.*;

import java.util.Collection;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FullUserDetailsDto {
    private String username;
    private String firstName;
    private String lastName;
    private String description;
    private Collection<ContactDto> contacts;
    private SettingsDto settings;


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
