package com.devwork.weekend.follow.repository;

import com.devwork.weekend.follow.domain.Follow;
import com.devwork.weekend.follow.domain.FollowId;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    public boolean existsByUserIdAndFollowId(long userId, long followId);

}
