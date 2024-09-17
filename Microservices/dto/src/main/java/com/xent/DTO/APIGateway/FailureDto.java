package com.xent.DTO.APIGateway;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FailureDto {
    private String serviceName;
    private String reason;
    private String rollbackIdentification;

    @Override
    public String toString() {
        return "FailureDto{" +
                "serviceName='" + serviceName + '\'' +
                ", reason=" + reason +
                ", rollbackIdentification='" + rollbackIdentification + '\'' +
                '}';
    }
}
