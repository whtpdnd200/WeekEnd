package com.devwork.weekend.user;

import com.devwork.weekend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    public List<User> findByMemberId(String memberId);
}
