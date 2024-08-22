package com.example.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserIsNotInContactsException extends ResponseStatusException {
    public UserIsNotInContactsException() {
        super(HttpStatus.BAD_REQUEST, "Users are not contacts");
    }
}
