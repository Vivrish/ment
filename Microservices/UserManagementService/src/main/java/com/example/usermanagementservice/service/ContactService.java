package com.example.usermanagementservice.service;

import com.example.usermanagementservice.DTO.ContactDto;
import com.example.usermanagementservice.domain.Contact;
import com.example.usermanagementservice.domain.User;
import com.example.usermanagementservice.exception.UserAlreadyInContactsException;
import com.example.usermanagementservice.exception.UserDoesNotExistException;
import com.example.usermanagementservice.exception.UserIsNotInContactsException;
import com.example.usermanagementservice.repository.ContactRepository;
import com.example.usermanagementservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
@Transactional
@AllArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public Collection<ContactDto> getContactsByNickname(String nickname) throws UserDoesNotExistException{
        User user = userRepository.getByNickname(nickname);
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        Collection<ContactDto> contacts = new ArrayList<>();
        for (Contact contact: user.getContacts()) {
            contacts.add(new ContactDto(contact));
        }
        return contacts;
    }

    public boolean areContacts(String nicknameOne, String nicknameOther) throws UserDoesNotExistException {
        User userOne = userRepository.getByNickname(nicknameOne);
        User userOther = userRepository.getByNickname(nicknameOther);
        if (userOne == null || userOther == null) {
            throw new UserDoesNotExistException();
        }
        return contactRepository.existsByUserAndContact(userOne, userOther);
    }

    public ContactDto addContactByNicknames(String nicknameOne, String nicknameOther) throws  UserDoesNotExistException, UserAlreadyInContactsException{
        User userOne = userRepository.getByNickname(nicknameOne);
        User userOther = userRepository.getByNickname(nicknameOther);
        if (areContacts(userOne.getNickname(), userOther.getNickname())) {
            throw new UserAlreadyInContactsException();
        }
        Contact contact = new Contact(userOne, userOther);
        contactRepository.save(contact);
        Contact inverseContact = new Contact(userOther, userOne);
        contactRepository.save(inverseContact);
        return new ContactDto(contact);
    }

    public void deleteContactByNicknames(String nicknameOne, String nicknameOther) throws  UserDoesNotExistException, UserIsNotInContactsException{
        User userOne = userRepository.getByNickname(nicknameOne);
        User userOther = userRepository.getByNickname(nicknameOther);
        if (!areContacts(userOne.getNickname(), userOther.getNickname())) {
            throw new UserIsNotInContactsException();
        }
        contactRepository.deleteByUserAndContact(userOne, userOther);
        contactRepository.deleteByUserAndContact(userOther, userOne);
    }


}
