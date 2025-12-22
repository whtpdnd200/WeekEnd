package com.devwork.weekend.user.UserDTO;

import com.devwork.weekend.follow.followDTO.FollowCountDTO;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class UserInfoDTO {

    private long id;
    private String name;
    private String profileImage;
    private FollowCountDTO followCountDTO;
    private boolean isFollow;
    private boolean isFollower;
}
