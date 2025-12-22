package com.devwork.weekend.follow.repository;

import com.devwork.weekend.follow.domain.Follow;
import com.devwork.weekend.follow.domain.FollowId;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    public boolean existsByUserIdAndFollowId(long userId, long followId);

    public Optional<Follow> findByUserIdAndFollowId(long userId, long followId);

    public Slice<Follow> findByUserId(long userId);

    public Slice<Follow> findByFollowId(long userId);

    public int countByUserId(long userId);

    public int countByFollowId(long followId);
}
