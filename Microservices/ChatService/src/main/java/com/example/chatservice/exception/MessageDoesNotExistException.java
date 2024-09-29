package com.example.chatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class MessageDoesNotExistException extends ResponseStatusException {
    public MessageDoesNotExistException() {
        super(HttpStatus.NOT_FOUND, "Message does not exist");
    }
}
