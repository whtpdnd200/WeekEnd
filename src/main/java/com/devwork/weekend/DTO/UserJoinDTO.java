package com.devwork.weekend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserJoinDTO {

    private long id;
    private String memberId;
    private String password;
    private String name;
    private String email;

}
