package com.example.usermanagementservice.config;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "UserManagementService")
public interface FeignConfig {


}
