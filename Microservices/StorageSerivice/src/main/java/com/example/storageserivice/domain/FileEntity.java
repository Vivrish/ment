package com.example.storageserivice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private byte[] data;

    public FileEntity(String name, String type, byte[] bytes) {
        this.name = name;
        this.type = type;
        this.data = bytes;
    }
}
