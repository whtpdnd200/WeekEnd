package com.devwork.weekend.like.repository;

import com.devwork.weekend.like.domain.Like;
import com.devwork.weekend.like.domain.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    public int countByPostId(long postId);
}
