package com.devwork.weekend.follow.service;

import com.devwork.weekend.follow.domain.Follow;
import com.devwork.weekend.follow.followDTO.FollowCountDTO;
import com.devwork.weekend.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean followDelete(long userId, long followId) {

        Optional<Follow> optionalFollow = followRepository.findByUserIdAndFollowId(userId, followId);

        if(optionalFollow.isPresent()) {
            Follow follow = optionalFollow.get();
            try {
                followRepository.delete(follow);
            } catch(DataAccessException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public List<Long> getFollowList(long userId) {
        List<Follow> follows = followRepository.findByUserId(userId).getContent();
        List<Long> followUserList = new ArrayList<>();

        for(Follow follow : follows) {
            followUserList.add(follow.getFollowId());
        }
        return followUserList;
    }

    public boolean isFollow(long userId, long followId) {

        return followRepository.existsByUserIdAndFollowId(userId, followId);
    }

    public FollowCountDTO getCounts(long userId) {

        FollowCountDTO followCountDTO = FollowCountDTO.builder()
                .followerCount(followRepository.countByFollowId(userId))
                .followingCount(followRepository.countByUserId(userId))
                .build();

        return followCountDTO;
    }
}
