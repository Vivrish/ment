package com.example.authenticationservice.service;

import com.example.authenticationservice.DTO.RoleDto;
import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.repository.RoleRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PutMapping("/role")
    public void addRole(@RequestBody RoleDto role) {
        roleRepository.save(new Role(role.getName()));
    }
}
