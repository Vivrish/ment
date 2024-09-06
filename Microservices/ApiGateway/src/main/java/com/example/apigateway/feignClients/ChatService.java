package com.example.apigateway.feignClients;


import com.example.apigateway.DTO.FullChatUserDto;
import com.example.apigateway.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ChatService", configuration = FeignConfig.class)
@Component
public interface ChatService {

    @GetMapping("api/v1/users/{username}")
    FullChatUserDto getUserByName(@PathVariable String username);
}
