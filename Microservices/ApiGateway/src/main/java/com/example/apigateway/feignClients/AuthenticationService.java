package com.example.apigateway.feignClients;

import com.xent.DTO.AuthenticationService.UserCredentialsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AuthenticationService")
@Component
public interface AuthenticationService {
    @PostMapping("/api/v1/register")
    void register(@RequestBody UserCredentialsDto user);
    @PostMapping("/api/v1/login")
    String login(@RequestBody UserCredentialsDto user);
    @GetMapping("/api/v1/auth/{token}")
    boolean authenticate(@PathVariable String token);
    @GetMapping("api/v1/users/{username}")
    UserCredentialsDto getUserByName(@PathVariable String username);

}
