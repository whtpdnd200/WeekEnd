package com.devwork.weekend.passwordReset.PasswordResetDTO;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PasswordResetBasicDTO {

    private long id;
    private String email;
    private String code;
    private boolean used;
    private LocalDateTime expiredAt;

}
