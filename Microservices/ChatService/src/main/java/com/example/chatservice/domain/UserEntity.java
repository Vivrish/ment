package com.example.chatservice.domain;


import com.xent.DTO.ChatService.ShortChatUserDto;
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
    private Long id;
    @Column(unique = true, nullable = false)
    private String nickname;
    @ManyToMany(mappedBy = "members")
    private Collection<RoomEntity> rooms = new ArrayList<>();
    @ManyToMany(mappedBy = "connectedMembers")
    private Collection<RoomEntity> connections = new ArrayList<>();
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<MessageEntity> messages = new ArrayList<>();

    public UserEntity(ShortChatUserDto shortUserDto) {
        this.nickname = shortUserDto.getUsername();
    }

    public void setFields(ShortChatUserDto userToEdit) {
        this.nickname = userToEdit.getUsername();
    }
}
