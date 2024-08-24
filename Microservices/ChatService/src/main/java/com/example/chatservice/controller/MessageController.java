package com.example.chatservice.controller;

import com.example.chatservice.DTO.FullMessageDto;
import com.example.chatservice.DTO.ShortMessageDto;
import com.example.chatservice.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;


    @GetMapping("/{id}")
    public FullMessageDto getMessage(@PathVariable Long id) {
        return messageService.getMessage(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }

    @PostMapping("")
    public FullMessageDto addMessage(@RequestBody ShortMessageDto shortMessageDto) {
        return messageService.addMessage(shortMessageDto);
    }

    @PutMapping("/{id}")
    public FullMessageDto editMessage(@RequestBody ShortMessageDto shortMessageDto, @PathVariable Long id) {
        return messageService.editMessage(id, shortMessageDto);
    }


}
