package com.devwork.weekend.user.service;

import com.devwork.weekend.DTO.UserJoinDTO;
import com.devwork.weekend.common.MD5HashingEncoder;
import com.devwork.weekend.user.repository.UserRepository;
import com.devwork.weekend.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public boolean create(UserJoinDTO dto) {

        String encodedPassword = MD5HashingEncoder.encode(dto.getPassword());
        User user = User.builder()
                .memberId(dto.getMemberId())
                .password(encodedPassword)
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
        return userRepository.save(user) != null;
    }

    public boolean isDuplicateId(String id) {

        return !userRepository.findByMemberId(id).isEmpty();
    }

    public boolean userLogin(String memberId, String password) {

        String encodedPassword = MD5HashingEncoder.encode(password);
        return userRepository.findByMemberIdAndPassword(memberId, encodedPassword) != null;
    }
}
