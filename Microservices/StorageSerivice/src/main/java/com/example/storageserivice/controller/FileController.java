package com.example.storageserivice.controller;

import com.example.storageserivice.DTO.FileDto;
import com.example.storageserivice.domain.FileEntity;
import com.example.storageserivice.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/files")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("")
    public Collection<FileDto> getAllFiles() {
        return fileService.getAllFiles();
    }
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable Long id) {
        FileEntity fileEntity = fileService.getFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +fileEntity.getName() + "\"")
                .body(fileEntity.getData());
    }
    @PostMapping("")
    public Long uploadFile(@RequestParam MultipartFile file) {
        return fileService.saveFile(file);
    }
    @DeleteMapping("/{id}")
    public void deleteFileById(@PathVariable Long id) {
        fileService.deleteById(id);
    }

}
