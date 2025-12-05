package com.devwork.weekend.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyDTO {

    private long id;
    private String name;
    private String email;
    private String profileImage;
}
