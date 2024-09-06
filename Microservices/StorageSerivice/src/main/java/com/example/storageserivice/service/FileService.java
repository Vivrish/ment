package com.example.storageserivice.service;


import com.example.storageserivice.DTO.FileDto;
import com.example.storageserivice.domain.FileEntity;
import com.example.storageserivice.exception.IdDoesNotExistException;
import com.example.storageserivice.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public FileEntity getFileById(Long id) {
        return fileRepository.findById(id).orElseThrow(IdDoesNotExistException::new);
    }

    public Long saveFile(MultipartFile file)  {
        FileEntity fileEntity;
        try {
            fileEntity = new FileEntity(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        } catch (IOException e) {
            throw new com.example.storageserivice.exception.IOException();
        }

        fileRepository.save(fileEntity);
        return fileEntity.getId();
    }

    public Collection<FileDto> getAllFiles() {
        Collection<FileDto> filesDto = new ArrayList<>();
        for (FileEntity fileEntity: fileRepository.findAll()) {
            filesDto.add(new FileDto(fileEntity));
        }
        return filesDto;
    }

    public void deleteById(Long id) {
        fileRepository.deleteById(id);
    }
}
