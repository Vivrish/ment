package com.example.chatservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class TopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String topicName;
    @OneToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    RoomEntity room;

    public TopicEntity(RoomEntity room) {
        this.room = room;
        this.topicName = "topic-room-" + room.getName();
    }
}
