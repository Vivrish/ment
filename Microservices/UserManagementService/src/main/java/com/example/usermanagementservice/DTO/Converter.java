package com.example.usermanagementservice.DTO;

import com.example.usermanagementservice.domain.Contact;
import com.example.usermanagementservice.domain.Settings;
import com.example.usermanagementservice.domain.User;
import com.xent.DTO.UserManagementService.ContactDto;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
import com.xent.DTO.UserManagementService.SettingsDto;
import com.xent.DTO.UserManagementService.ShortUserDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    public ContactDto contactDto(Contact contact) {
        ContactDto contactDto = new ContactDto();
        contactDto.setContact(shortUserDetailsDto(contact.getContact()));
        contactDto.setUser(shortUserDetailsDto(contact.getUser()));
        contactDto.setCreatedAt(contact.getCreatedAt());
        return contactDto;
    }

    public SettingsDto settingsDto(Settings settings) {
        SettingsDto settingsDto = new SettingsDto();
        settingsDto.setAllowInvites(settings.isAllowInvites());
        settingsDto.setAllowMessagesFromStrangers(settings.isAllowMessagesFromStrangers());
        return settingsDto;
    }


    public ShortUserDetailsDto shortUserDetailsDto(User user) {
        FullUserDetailsDto fullUserDetailsDto = fullUserDetailsDto(user);
        return new ShortUserDetailsDto(fullUserDetailsDto);
    }

    public FullUserDetailsDto fullUserDetailsDto(User user) {
        FullUserDetailsDto userDetailsDto = new FullUserDetailsDto();
        if (user.getSettings() != null) {
            userDetailsDto.setSettings(settingsDto(user.getSettings()));
        }
        for (Contact contact: user.getContacts()) {
            userDetailsDto.addContact(contactDto(contact));
        }
        userDetailsDto.setDescription(user.getDescription());
        userDetailsDto.setUsername(user.getNickname());
        userDetailsDto.setFirstName(user.getFirstName());
        userDetailsDto.setLastName(user.getLastName());
        return userDetailsDto;
    }
}
