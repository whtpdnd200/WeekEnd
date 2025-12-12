package com.devwork.weekend.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
public class UserModifyDTO {

    private String name;
    private String password;
    private String email;
    private MultipartFile profileImage;
}
