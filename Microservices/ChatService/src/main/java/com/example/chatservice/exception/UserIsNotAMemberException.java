package com.example.chatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserIsNotAMemberException extends ResponseStatusException {

    public UserIsNotAMemberException() {
        super(HttpStatus.BAD_REQUEST, "User is not a member of this room");
    }
}
