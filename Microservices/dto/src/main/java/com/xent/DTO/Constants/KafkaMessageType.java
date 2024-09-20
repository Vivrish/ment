package com.xent.DTO.Constants;

import lombok.Getter;

@Getter
public enum KafkaMessageType {
    USER_REGISTRATION("USER_REGISTRATION"),
    USER_REGISTRATION_FAILURE("USER_REGISTRATION_FAILURE")
    ;
    private final String messageType;

    KafkaMessageType(String messageType) {
        this.messageType = messageType;
    }
}
