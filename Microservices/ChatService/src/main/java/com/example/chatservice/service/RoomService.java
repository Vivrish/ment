package com.example.chatservice.service;

import com.example.chatservice.DTO.FullRoomDto;
import com.example.chatservice.DTO.ShortRoomDto;
import com.example.chatservice.domain.RoomEntity;
import com.example.chatservice.domain.UserEntity;
import com.example.chatservice.exception.RoomDoesNotExistException;
import com.example.chatservice.exception.UserAlreadyAMemberException;
import com.example.chatservice.exception.UserDoesNotExistException;
import com.example.chatservice.exception.UserIsNotAMemberException;
import com.example.chatservice.repository.RoomRepository;
import com.example.chatservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

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

    public FullRoomDto addMember(String roomName, String username) {
        UserEntity userEntity = userRepository.findByNickname(username)
                .orElseThrow(UserDoesNotExistException::new);
        RoomEntity roomEntity = roomRepository.findByName(roomName)
                .orElseThrow(RoomDoesNotExistException::new);
        if (roomEntity.getMembers().contains(userEntity)) {
            throw new UserAlreadyAMemberException();
        }
        roomEntity.addMember(userEntity);
        return new FullRoomDto(roomRepository.save(roomEntity));
    }

    public FullRoomDto deleteMember(String roomName, String username) {
        UserEntity userEntity = userRepository.findByNickname(username)
                .orElseThrow(UserDoesNotExistException::new);
        RoomEntity roomEntity = roomRepository.findByName(roomName)
                .orElseThrow(RoomDoesNotExistException::new);
        if (!roomEntity.getMembers().contains(userEntity)) {
            throw new UserIsNotAMemberException();
        }
        roomEntity.deleteMember(userEntity);
        return new FullRoomDto(roomRepository.save(roomEntity));
    }
}
