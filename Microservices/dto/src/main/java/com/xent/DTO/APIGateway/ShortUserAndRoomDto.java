package com.xent.DTO.APIGateway;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUserAndRoomDto {
    private String username;
    private String roomName;
}
