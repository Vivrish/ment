package com.xent.DTO.Constants;

import lombok.Getter;

@Getter
public enum KafkaTopic {
    REGISTER("register"),
    CREATE_ROOM("create-room"),
    SEND_MESSAGE_HTTP("send-message-http");

    private final String topic;

    KafkaTopic(String topic) {
        this.topic = topic;
    }
}
