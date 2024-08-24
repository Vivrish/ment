package com.example.chatservice.domain;

import com.example.chatservice.DTO.ShortRoomDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "room")
@NoArgsConstructor
@Data
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    String name;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<MessageEntity> messages;
    @ManyToMany
    @JoinTable(name = "room_user_entity",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_entity_id"))
    Collection<UserEntity> members;

    public RoomEntity(ShortRoomDto shortRoomDto) {
        this.name = shortRoomDto.getName();
    }

    public void setFields(ShortRoomDto shortRoomDto) {
        this.name = shortRoomDto.getName();
    }
}
