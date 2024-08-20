package com.example.authenticationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectCredentialsException extends ResponseStatusException {

    public IncorrectCredentialsException() {
        super(HttpStatus.BAD_REQUEST, "Incorrect login or password");
    }
}
