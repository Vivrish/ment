package com.example.authenticationservice.repository;


import com.example.authenticationservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String username);

    boolean existsByName(String name);
}
