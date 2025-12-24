package com.devwork.weekend.like.service;

import com.devwork.weekend.like.domain.Like;
import com.devwork.weekend.like.domain.LikeId;
import com.devwork.weekend.like.repository.LikeRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {

        this.likeRepository = likeRepository;
    }

    public boolean createLike(long postId, long userId) {

        Like like = Like.builder()
                .postId(postId)
                .userId(userId)
                .build();

        try {
            likeRepository.save(like);

        } catch(DataAccessException e) {
            return false;
        }
        return true;
    }

    public boolean deleteLike(long postId, long userId) {

        LikeId likeId = LikeId.builder()
                .postId(postId)
                .userId(userId)
                .build();

        Optional<Like> optionalLike = likeRepository.findById(likeId);
        if(optionalLike.isPresent()) {

            try {
                likeRepository.delete(optionalLike.get());
            } catch(DataAccessException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public void deleteLikeByPostId(long postId) {

        likeRepository.deleteByPostId(postId);
    }

    public int getLikeCount(long postId) {

        return likeRepository.countByPostId(postId);
    }

    public boolean isLikeByPostIdAndUserId(long postId, long userId) {

        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }

    public List<Long> getLikeList(long userId) {

        List<Like> list = likeRepository.findByUserId(userId);

        List<Long> postIdList = new ArrayList<>();

        for(Like like : list) {
            postIdList.add(like.getPostId());
        }

        return postIdList;
    }
}
