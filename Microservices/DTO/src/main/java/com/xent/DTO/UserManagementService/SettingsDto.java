package com.xent.DTO.UserManagementService;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SettingsDto {
    private boolean allowMessagesFromStrangers;
    private boolean allowInvites;

}
