package com.devwork.weekend.user.UserDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserBasicDTO {

    private long id;
    private String memberId;
    private String name;
    private String profilePath;
    private boolean isFollow;
    private boolean isFollower;
}
