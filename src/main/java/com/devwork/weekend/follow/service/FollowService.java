package com.devwork.weekend.follow.service;

import com.devwork.weekend.follow.domain.Follow;
import com.devwork.weekend.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;

    public boolean createFollow(long userId, long followId) {

        Follow follow = Follow.builder()
                .userId(userId)
                .followId(followId)
                .build();

        try {
            followRepository.save(follow);
        } catch(DataAccessException e) {
            return false;
        }
        return true;
    }

    public boolean isFollow(long userId, long followId) {

        return followRepository.existsByUserIdAndFollowId(userId, followId);
    }
}
