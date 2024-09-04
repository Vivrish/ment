package com.example.apigateway.feignClients;

import com.example.apigateway.DTO.FullUserCredentialsDto;
import com.example.apigateway.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AuthenticationService", configuration = FeignConfig.class)
@Component
public interface AuthenticationService {
    @PostMapping("/api/v1/register")
    void register(@RequestBody FullUserCredentialsDto user);
    @PostMapping("/api/v1/login")
    String login(@RequestBody FullUserCredentialsDto user);
    @GetMapping("/api/v1/auth")
    boolean authenticate(String token);

}
