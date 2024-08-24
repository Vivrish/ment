package com.example.chatservice.controller;

import com.example.chatservice.DTO.FullRoomDto;
import com.example.chatservice.DTO.ShortRoomDto;
import com.example.chatservice.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/rooms")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;

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

    @PutMapping("/{name}")
    public FullRoomDto editRoom(@RequestBody ShortRoomDto shortRoomDto, @PathVariable String name) {
        return roomService.editRoom(name, shortRoomDto);
    }
}
