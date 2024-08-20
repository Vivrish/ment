package com.example.authenticationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NameAlreadyExistsException extends ResponseStatusException {
    public NameAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "Name already exists");
    }
}
