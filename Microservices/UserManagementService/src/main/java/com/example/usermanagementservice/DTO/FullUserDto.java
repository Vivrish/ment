package com.example.usermanagementservice.DTO;


import com.example.usermanagementservice.domain.Contact;
import com.example.usermanagementservice.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FullUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String description;
    private Collection<ContactDto> contacts;
    private SettingsDto settings;

    public FullUserDto(User user) {
        this.username = user.getNickname();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.description = user.getDescription();
        this.settings = new SettingsDto(user.getSettings());
        this.contacts = new ArrayList<>();
        for (Contact contact: user.getContacts()) {
            this.contacts.add(new ContactDto(contact));
        }
    }

    @Override
    public String toString() {
        return "FullUserDto{" +
                "nickname='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
