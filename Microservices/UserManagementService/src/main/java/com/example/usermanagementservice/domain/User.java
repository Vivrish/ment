package com.example.usermanagementservice.domain;

import com.example.usermanagementservice.DTO.FullUserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user_table")
@Getter
@Setter
@NoArgsConstructor
public class User {
    public User(String nickname, String firstName, String lastName, String description, Settings settings) {
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.settings = settings;
    }

    public User(FullUserDto userDto) {
        this.nickname = userDto.getUsername();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.description = userDto.getDescription();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String description;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Contact> contacts = new ArrayList<>();
    @OneToOne
    private Settings settings;


    public void updateAttributes(FullUserDto fullUserDto) {
        this.nickname = fullUserDto.getUsername();
        this.firstName = fullUserDto.getFirstName();
        this.lastName = fullUserDto.getLastName();
        this.description = fullUserDto.getDescription();
    }


    @Override
    public boolean equals(Object user) {
        if (user.getClass() != getClass()) {
            return false;
        }
        return Objects.equals(((User)user).nickname, nickname);
    }
}
