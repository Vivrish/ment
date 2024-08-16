package com.example.authenticationservice.repository;

import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
    User findUserByName(String username);

    Collection<Role> getUserRolesByName(String username);
}
