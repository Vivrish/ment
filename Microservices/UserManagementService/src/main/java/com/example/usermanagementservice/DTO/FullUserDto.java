package com.example.usermanagementservice.DTO;


import com.example.usermanagementservice.domain.Contact;
import com.example.usermanagementservice.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Getter
@Setter
public class FullUserDto {
    private String nickname;
    private String firstName;
    private String lastName;
    private String description;
    private Collection<ContactDto> contacts;
    private SettingsDto settings;

    public FullUserDto(User user) {
        this.nickname = user.getNickname();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.description = user.getDescription();
        this.settings = new SettingsDto(user.getSettings());
        this.contacts = new ArrayList<>();
        for (Contact contact: user.getContacts()) {
            this.contacts.add(new ContactDto(contact));
        }
    }
}
