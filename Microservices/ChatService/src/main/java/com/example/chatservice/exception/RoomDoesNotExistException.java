package com.example.chatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class RoomDoesNotExistException extends ResponseStatusException {
    public RoomDoesNotExistException() {
        super(HttpStatus.BAD_REQUEST, "Room does not exist");
    }
}
