package com.example.usermanagementservice.DTO;


import com.example.usermanagementservice.domain.Settings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SettingsDto {
    private boolean allowMessagesFromStrangers;
    private boolean allowInvites;

    public SettingsDto(Settings settings) {
        this.allowInvites = settings.isAllowInvites();
        this.allowMessagesFromStrangers = settings.isAllowMessagesFromStrangers();
    }
}
