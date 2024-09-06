package com.example.apigateway.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class FullChatUserDto {
    private String nickname;
    private Collection<ShortMessageDto> messages = new ArrayList<>();
    private Collection<ShortRoomDto> rooms = new ArrayList<>();
}
