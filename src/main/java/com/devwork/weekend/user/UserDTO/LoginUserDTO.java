package com.devwork.weekend.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserDTO {

    private long id;
    private String memberId;
    private String name;
    private String email;
    private String profileImage;
}
