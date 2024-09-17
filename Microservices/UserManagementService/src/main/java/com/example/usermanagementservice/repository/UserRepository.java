package com.example.usermanagementservice.repository;

import com.example.usermanagementservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByNickname(String nickname);

    boolean existsByNickname(String username);

    void deleteByNickname(String username);
}
