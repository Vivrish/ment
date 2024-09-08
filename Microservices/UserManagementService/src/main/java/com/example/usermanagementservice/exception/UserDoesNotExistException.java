package com.example.usermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserDoesNotExistException extends ResponseStatusException {
    public UserDoesNotExistException() {
        super(HttpStatus.NOT_FOUND, "User with this nickname does not exist");
    }
}
