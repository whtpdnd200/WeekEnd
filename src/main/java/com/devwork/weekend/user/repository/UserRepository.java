package com.devwork.weekend.user.repository;

import com.devwork.weekend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    public boolean existsByMemberId(String memberId);

    public Optional<User> findByMemberIdAndPassword(String memberId, String password);


}
