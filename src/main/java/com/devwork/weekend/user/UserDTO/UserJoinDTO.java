package com.devwork.weekend.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinDTO {

    private String memberId;
    private String password;
    private String name;
    private String email;

}
