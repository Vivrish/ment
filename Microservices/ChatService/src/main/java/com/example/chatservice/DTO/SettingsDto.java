package com.example.chatservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SettingsDto {
    private boolean allowMessagesFromStrangers;
    private boolean allowInvites;
}
