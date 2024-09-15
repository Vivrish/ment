package com.xent.DTO.AuthenticationService;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RoleDto {
    private String name;

    public RoleDto(String name) {
        this.name = name;
    }


}
