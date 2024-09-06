package com.example.storageserivice.DTO;

import com.example.storageserivice.domain.FileEntity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FileDto {
    private String name;
    private String type;
    private byte[] data;
    public FileDto(FileEntity fileEntity) {
        this.name = fileEntity.getName();
        this.type = fileEntity.getType();
        this.data = fileEntity.getData();
    }
}
