package com.example.apigateway.feignClients;


import com.example.apigateway.DTO.FullUserDetailsDto;
import com.example.apigateway.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserManagementService", configuration = FeignConfig.class)
@Component
public interface UserManagementService {

    @GetMapping("api/v1/users/{username}")
    FullUserDetailsDto getUserByName(@PathVariable String username);

}
