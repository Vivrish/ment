package com.example.apigateway.controller;


import com.example.apigateway.service.AuthService;
import com.example.apigateway.service.RoomService;
import com.xent.DTO.ChatService.FullRoomDto;
import com.xent.DTO.ChatService.ShortRoomDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/rooms")
@AllArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;
    private final AuthService authService;

    @PostMapping("")
    public ResponseEntity<Void> makeRoom(@RequestBody ShortRoomDto room, @RequestHeader("Authorization") String auth) {
        log.debug("Request accepted: make room {}", room);
        authService.authenticate(auth);
        roomService.makeRoom(room);
        return ResponseEntity.accepted().build();
    }

}
