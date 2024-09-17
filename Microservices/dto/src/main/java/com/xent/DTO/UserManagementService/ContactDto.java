package com.xent.DTO.UserManagementService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactDto {
    private ShortUserDetailsDto user;
    private ShortUserDetailsDto contact;
    private LocalDateTime createdAt;
}
