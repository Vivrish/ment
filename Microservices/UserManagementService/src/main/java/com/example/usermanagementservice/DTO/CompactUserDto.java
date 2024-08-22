package com.example.usermanagementservice.DTO;
import com.example.usermanagementservice.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@AllArgsConstructor
@Getter
@Setter
public class CompactUserDto {
    private String nickname;
    private String firstName;
    private String lastName;
    private String description;
    public CompactUserDto(User user) {
        this.nickname = user.getNickname();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.description = user.getDescription();
    }
}
