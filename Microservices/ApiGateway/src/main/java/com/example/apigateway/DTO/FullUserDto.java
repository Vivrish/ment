package com.example.apigateway.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    private SettingsDto settings;
    private Collection<RoleDto> roles = new ArrayList<>();
    private Collection<ShortUserDetailsDto> contacts = new ArrayList<>();
    private Collection<ShortMessageDto> messages = new ArrayList<>();
    private Collection<ShortRoomDto> rooms = new ArrayList<>();

    public FullUserDto(String username, String password, String firstName, String lastName, String description) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }


    public void updateAttributes(@NonNull FullUserCredentialsDto credentials) {
        this.username = credentials.getNickname();
        this.password = credentials.getPassword();
        this.roles = credentials.getRoles();
    }
    public void updateAttributes(@NonNull FullUserDetailsDto userDetails) {
        this.firstName = userDetails.getFirstName();
        this.lastName = userDetails.getLastName();
        this.description = userDetails.getDescription();
        this.contacts = userDetails.getContacts();
        this.settings = userDetails.getSettings();
    }

    public void updateAttributes(@NonNull FullChatUserDto chatUser) {
        this.rooms = chatUser.getRooms();
        this.messages = chatUser.getMessages();
    }


    @Override
    public String toString() {
        return "FullUserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", settings=" + settings +
                ", roles=" + roles +
                ", contacts=" + contacts +
                ", messages=" + messages +
                ", rooms=" + rooms +
                '}';
    }
}
