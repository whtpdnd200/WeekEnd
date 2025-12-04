package com.devwork.weekend.user.service;

import com.devwork.weekend.common.MD5HashingEncoder;
import com.devwork.weekend.user.UserRepository;
import com.devwork.weekend.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public boolean create(User user) {

        String encodedPassword = MD5HashingEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user) != null;
    }

    public boolean isDuplicateId(String id) {

        return !userRepository.findByMemberId(id).isEmpty();
    }
}
