package com.example.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyInContactsException extends ResponseStatusException {
    public UserAlreadyInContactsException() {
        super(HttpStatus.BAD_REQUEST, "Users are already contacts");
    }
}
