package com.devwork.weekend.user.repository;

import com.devwork.weekend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    public boolean existsByMemberId(String memberId);

    public Optional<User> findByMemberIdAndPassword(String memberId, String password);

    @Query("""
           SELECT u FROM User u
           WHERE u.id IN(:followingList)
           ORDER BY u.id DESC
           """)
    public List<User> findAllByFollowingList(List<Long> followingList);

    @Query("""
           SELECT u FROM User u
           WHERE u.id IN(:followerList)
           ORDER BY u.id DESC
           """)
    public List<User> findAllByFollowerList(List<Long> followerList);

    public Optional<User> findByEmail(String email);
}
