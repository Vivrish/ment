package com.example.chatservice.exception;

import org.springframework.http.HttpStatus;

import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyAMemberException extends ResponseStatusException {
    public UserAlreadyAMemberException() {
        super(HttpStatus.BAD_REQUEST, "User is already a member of this room");
    }
}
