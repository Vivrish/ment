package com.example.chatservice.repository;

import com.example.chatservice.domain.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    boolean existsByName(String name);
    Optional<RoomEntity> findByName(String name);
    void deleteByName(String name);
}
