package com.example.usermanagementservice.DTO;

import com.example.usermanagementservice.domain.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ContactDto {
    private CompactUserDto user;
    private CompactUserDto contact;
    private LocalDateTime createdAt;

    public ContactDto(Contact domainContact) {
        this.user = new CompactUserDto(domainContact.getUser());
        this.contact = new CompactUserDto(domainContact.getContact());
        this.createdAt = domainContact.getCreatedAt();
    }

}
