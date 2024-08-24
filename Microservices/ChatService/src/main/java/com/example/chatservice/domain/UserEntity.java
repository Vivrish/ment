package com.example.chatservice.domain;

import com.example.chatservice.DTO.ShortUserDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "user_entity")
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    String nickname;
    @ManyToMany(mappedBy = "members")
    Collection<RoomEntity> rooms = new ArrayList<>();
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    Collection<MessageEntity> messages = new ArrayList<>();

    public UserEntity(ShortUserDto shortUserDto) {
        this.nickname = shortUserDto.getUsername();
    }

    public void setFields(ShortUserDto userToEdit) {
        this.nickname = userToEdit.getUsername();
    }
}
