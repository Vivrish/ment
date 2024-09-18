package com.example.apigateway.controller;


import com.example.apigateway.service.AuthService;
import com.example.apigateway.service.MessageService;
import com.xent.DTO.ChatService.ShortMessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/messages")
@AllArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;
    private final AuthService authService;

    @PostMapping("")
    public ResponseEntity<Void> sendMessageHttp(@RequestBody ShortMessageDto message, @RequestHeader("Authorization") String auth) {
        log.debug("Request accepted: send message via HTTP: {}", message);
        authService.authenticate(auth);
        messageService.sendMessage(message);
        return ResponseEntity.accepted().build();
    }

}
