package com.example.authenticationservice.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NameDoesNotExistException extends ResponseStatusException {
    public NameDoesNotExistException() {
        super(HttpStatusCode.valueOf(404), "Name does not exist");
    }
}
