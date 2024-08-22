package com.example.storageserivice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class IdDoesNotExistException extends ResponseStatusException {
    public IdDoesNotExistException() {
        super(HttpStatus.NOT_FOUND, "File with provided id does not exist");
    }
}
