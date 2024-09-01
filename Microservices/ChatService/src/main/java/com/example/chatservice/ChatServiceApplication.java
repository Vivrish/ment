package com.example.chatservice;

import com.example.chatservice.DTO.ShortMessageDto;
import com.example.chatservice.DTO.ShortRoomDto;
import com.example.chatservice.DTO.ShortUserDto;
import com.example.chatservice.service.MessageService;
import com.example.chatservice.service.RoomService;
import com.example.chatservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, RoomService roomService, MessageService messageService) {

        return args -> {
            ShortUserDto userOne = new ShortUserDto();
            userOne.setUsername("pablo");

            ShortUserDto userTwo = new ShortUserDto();
            userTwo.setUsername("travis");

            userService.addUser(userOne);
            userService.addUser(userTwo);

            ShortRoomDto roomOne = new ShortRoomDto();
            ShortRoomDto roomTwo = new ShortRoomDto();

            roomOne.setName("vrsovice");
            roomTwo.setName("liben");

            roomService.createRoom(roomOne);
            roomService.createRoom(roomTwo);

            roomService.addMember("vrsovice", "pablo");
            roomService.addMember("liben", "pablo");
            roomService.addMember("vrsovice", "travis");

            ShortMessageDto message = new ShortMessageDto();
            message.setMessage("Bruh");
            message.setSenderName("travis");
            message.setRoomName("vrsovice");
            messageService.addMessage(message);

        };
    }
}
