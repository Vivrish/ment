package com.example.authenticationservice.repository;

import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String username);

    Collection<Role> getUserRolesByName(String username);

    boolean existsByName(String name);
}
