package com.example.storageserivice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class IOException extends ResponseStatusException {
    public IOException() {
        super(HttpStatusCode.valueOf(500), "A problem with I/O operation on file");
    }
}
