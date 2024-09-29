package com.example.usermanagementservice.domain;


import com.xent.DTO.UserManagementService.SettingsDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "settings")
@Getter
@Setter
@NoArgsConstructor
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private boolean allowMessagesFromStrangers = true;
    @Column
    private boolean allowInvites = true;

    public Settings(boolean allowMessagesFromStrangers, boolean allowInvites) {
        this.allowMessagesFromStrangers = allowMessagesFromStrangers;
        this.allowInvites = allowInvites;
    }

    public Settings(SettingsDto settingsDto) {
        this.allowInvites = settingsDto.isAllowInvites();
        this.allowMessagesFromStrangers = settingsDto.isAllowMessagesFromStrangers();
    }

    public void updateAttributes(SettingsDto settingsDto) {
        this.allowInvites = settingsDto.isAllowInvites();
        this.allowMessagesFromStrangers = settingsDto.isAllowMessagesFromStrangers();
    }

}
