package com.example.chatservice.domain;

import com.example.chatservice.DTO.ShortMessageDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@NoArgsConstructor
@Data
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)
    private LocalDateTime timeStamp = LocalDateTime.now();
    @Column
    private String message;
    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_entity_id", nullable = false)
    private UserEntity sender;

    public MessageEntity(ShortMessageDto shortMessageDto) {
        this.message = shortMessageDto.getMessage();
    }

    public void setFields(ShortMessageDto shortMessageDto) {
        this.message = shortMessageDto.getMessage();
    }
}
