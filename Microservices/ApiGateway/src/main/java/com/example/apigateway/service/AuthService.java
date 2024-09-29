package com.example.apigateway.service;

import com.example.apigateway.feignClients.AuthenticationService;
import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationService authenticationService;

    public void authenticate(String authHeader) {
        log.info("Authorizing header: {}", authHeader);
        if (!authenticationService.authenticate(authHeader)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401));
        }
    }
    public String login(@NonNull UserCredentialsDto credentials) {
        log.debug("Trying to log in user: {}", credentials);
        return authenticationService.login(credentials);
    }
}
