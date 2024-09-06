package com.example.chatservice.repository;

import com.example.chatservice.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByNickname(String nickname);
    Optional<UserEntity> findByNickname(String nickname);

    void deleteByNickname(String nickname);
}
