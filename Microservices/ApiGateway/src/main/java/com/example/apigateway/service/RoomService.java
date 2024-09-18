package com.example.apigateway.service;

import com.example.apigateway.feignClients.ChatService;
import com.xent.DTO.ChatService.FullRoomDto;
import com.xent.DTO.ChatService.ShortRoomDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RoomService {
    private final KafkaTemplate<String, ShortRoomDto> roomTemplate;

    public void makeRoom(ShortRoomDto room) {
        log.debug("Sending a kafka request to make a room to chat service");
        roomTemplate.send("create-room", room);
    }
}
