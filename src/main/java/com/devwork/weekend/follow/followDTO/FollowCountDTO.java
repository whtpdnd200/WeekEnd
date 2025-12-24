package com.devwork.weekend.follow.followDTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FollowCountDTO {

    private int followingCount;
    private int followerCount;
}
