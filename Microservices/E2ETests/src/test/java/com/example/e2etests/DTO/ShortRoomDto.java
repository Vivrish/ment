package com.example.e2etests.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortRoomDto {
    private String name;
    private String topicName;
    private Collection<String> memberNames = new ArrayList<>();
    private Collection<String> messages = new ArrayList<>();
    private Collection<String> connectedMemberNames = new ArrayList<>();
}
