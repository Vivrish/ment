package com.example.usermanagementservice.domain;


import com.example.usermanagementservice.DTO.SettingsDto;
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
    private boolean allowMessagesFromStrangers;
    @Column
    private boolean allowInvites;

    public Settings(boolean allowMessagesFromStrangers, boolean allowInvites) {
        this.allowMessagesFromStrangers = allowMessagesFromStrangers;
        this.allowInvites = allowInvites;
    }

    public void updateAttributes(SettingsDto settingsDto) {
        this.allowInvites = settingsDto.isAllowInvites();
        this.allowMessagesFromStrangers = settingsDto.isAllowMessagesFromStrangers();
    }

}
