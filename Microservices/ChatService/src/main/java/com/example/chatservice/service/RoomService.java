package com.example.chatservice.service;

import com.example.chatservice.DTO.FullRoomDto;
import com.example.chatservice.DTO.ShortRoomDto;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.exception.RoomDoesNotExistException;
import com.example.chatservice.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public FullRoomDto getRoomByName(String name) {
        RoomEntity roomEntity = getRoomOrThrow(name);
        return new FullRoomDto(roomEntity);
    }

    public FullRoomDto createRoom(ShortRoomDto shortRoomDto) {
        RoomEntity roomEntity = new RoomEntity(shortRoomDto);
        return new FullRoomDto(roomRepository.save(roomEntity));
    }

    public FullRoomDto editRoom(String name, ShortRoomDto shortRoomDto) {
        RoomEntity roomEntity = getRoomOrThrow(name);
        roomEntity.setFields(shortRoomDto);
        return new FullRoomDto(roomRepository.save(roomEntity));
    }

    public void deleteRoom(String name) {
        if (!roomRepository.existsByName(name)) {
            throw new RoomDoesNotExistException();
        }
        roomRepository.deleteByName(name);
    }


    private RoomEntity getRoomOrThrow(String name) throws RoomDoesNotExistException  {
        return roomRepository.findByName(name).orElseThrow(RoomDoesNotExistException::new);
    }
}
