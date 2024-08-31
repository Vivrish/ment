package com.example.chatservice.controller;

import com.example.chatservice.DTO.FullRoomDto;
import com.example.chatservice.DTO.ShortRoomDto;
import com.example.chatservice.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/rooms")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("")
    public Collection<FullRoomDto> getAll() {
        return roomService.getAll();
    }

    @GetMapping("/{name}")
    public FullRoomDto getByName(@PathVariable String name) {
        return roomService.getRoomByName(name);
    }

    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable String name) {
        roomService.deleteRoom(name);
    }

    @PostMapping("")
    public FullRoomDto createRoom(@RequestBody ShortRoomDto shortRoomDto) {
        return roomService.createRoom(shortRoomDto);
    }

    @PostMapping("/{roomName}/{username}")
    public FullRoomDto addMember(@PathVariable String roomName, @PathVariable String username) {
        return roomService.addMember(roomName, username);
    }


    @DeleteMapping("/{roomName}/{username}")
    public FullRoomDto deleteMember(@PathVariable String roomName, @PathVariable String username) {
        return roomService.deleteMember(roomName, username);
    }

    @PutMapping("/{name}")
    public FullRoomDto editRoom(@RequestBody ShortRoomDto shortRoomDto, @PathVariable String name) {
        return roomService.editRoom(name, shortRoomDto);
    }
}
