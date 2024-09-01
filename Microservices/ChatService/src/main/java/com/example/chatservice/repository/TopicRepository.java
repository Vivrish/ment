package com.example.chatservice.repository;

import com.example.chatservice.domain.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
    Optional<TopicEntity> findByTopicName(String name);

    void deleteByTopicName(String name);

    boolean existsByTopicName(String name);
}
