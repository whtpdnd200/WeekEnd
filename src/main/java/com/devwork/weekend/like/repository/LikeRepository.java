package com.devwork.weekend.like.repository;

import com.devwork.weekend.like.domain.Like;
import com.devwork.weekend.like.domain.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    public int countByPostId(long postId);

    public boolean existsByPostIdAndUserId(long postId, long userId);

    public Optional<Like> findByPostIdAndUserId(long postId, long userId);

    public List<Like> findByUserId(long userId);
}
