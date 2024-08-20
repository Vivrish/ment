package com.example.authenticationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoleDoesNotExistException extends ResponseStatusException {
        public RoleDoesNotExistException() {
            super(HttpStatus.BAD_REQUEST, "Incorrect role");
        }

}
