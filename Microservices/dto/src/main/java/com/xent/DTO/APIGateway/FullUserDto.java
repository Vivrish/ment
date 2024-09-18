package com.xent.DTO.APIGateway;

import com.xent.DTO.AuthenticationService.RoleDto;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import com.xent.DTO.ChatService.FullChatUserDto;
import com.xent.DTO.ChatService.ShortMessageDto;
import com.xent.DTO.ChatService.ShortRoomDto;
import com.xent.DTO.UserManagementService.ContactDto;
import com.xent.DTO.UserManagementService.FullUserDetailsDto;
import com.xent.DTO.UserManagementService.SettingsDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.*;

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
    private Collection<ContactDto> contacts = new ArrayList<>();
    private Collection<ShortMessageDto> messages = new ArrayList<>();
    private Collection<ShortRoomDto> rooms = new ArrayList<>();
    private Collection<ShortRoomDto> connectedRooms = new ArrayList<>();
    public FullUserDto(String username, String password, String firstName, String lastName, String description) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }


    public void updateAttributes(@NonNull UserCredentialsDto credentials) {
        this.username = credentials.getUsername();
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

    public boolean isEquivalent(FullUserDto other) {
        List<ShortRoomDto> thisSortedRooms = new ArrayList<>(this.rooms);
        thisSortedRooms.sort(Comparator.comparing(ShortRoomDto::getName));
        List<ShortRoomDto> otherSortedRooms = new ArrayList<>(other.getRooms());
        otherSortedRooms.sort(Comparator.comparing(ShortRoomDto::getName));

        List<ShortMessageDto> thisSortedMessages = new ArrayList<>(this.messages);
        thisSortedMessages.sort(Comparator.comparing(ShortMessageDto::getMessage));
        List<ShortMessageDto> otherSortedMessages = new ArrayList<>(other.getMessages());
        otherSortedMessages.sort(Comparator.comparing(ShortMessageDto::getMessage));

        List<RoleDto> thisSortedRoles = new ArrayList<>(this.roles);
        thisSortedRoles.sort(Comparator.comparing(RoleDto::getName));
        List<RoleDto> otherSortedRoles = new ArrayList<>(other.getRoles());
        otherSortedRoles.sort(Comparator.comparing(RoleDto::getName));


        return Objects.equals(this.username, other.getUsername())
                && Objects.equals(this.firstName, other.getFirstName())
                && Objects.equals(this.lastName, other.getLastName())
                && Objects.equals(this.description, other.getDescription())
                && thisSortedMessages.equals(otherSortedMessages)
                && thisSortedRooms.equals(otherSortedRooms)
                && thisSortedRoles.equals(otherSortedRoles);
    }


    @Override
    public String toString() {
        return "FullUserDetailsDto{" +
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
