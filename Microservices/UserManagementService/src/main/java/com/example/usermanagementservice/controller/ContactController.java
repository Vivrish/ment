package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.DTO.ContactDto;
import com.example.usermanagementservice.DTO.TwoNicknamesDto;
import com.example.usermanagementservice.service.ContactService;
import com.example.usermanagementservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/contacts")
@AllArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping("{username}" )
    public Collection<ContactDto> getContacts(@PathVariable String username) {
        return contactService.getContactsByNickname(username);
    }

    @PostMapping("")
    public ContactDto addContact(@RequestBody TwoNicknamesDto twoNicknamesDto) {
        return contactService.addContactByNicknames(twoNicknamesDto.getNicknameOne(), twoNicknamesDto.getNicknameOther());
    }
    @DeleteMapping("")
    public void deleteContact(@RequestBody TwoNicknamesDto twoNicknamesDto) {
        contactService.deleteContactByNicknames(twoNicknamesDto.getNicknameOne(), twoNicknamesDto.getNicknameOther());
    }
}
