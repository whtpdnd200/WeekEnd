package com.devwork.weekend.user.service;

import com.devwork.weekend.user.UserDTO.ModifyDTO;
import com.devwork.weekend.user.UserDTO.UserJoinDTO;
import com.devwork.weekend.common.SHA256HashingEncoder;
import com.devwork.weekend.user.repository.UserRepository;
import com.devwork.weekend.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public boolean createUser(UserJoinDTO userJoinDTO) {

        if(isDuplicateId(userJoinDTO.getMemberId())) {

            return false;
        }

        String encodedPassword = SHA256HashingEncoder.encode(userJoinDTO.getPassword());

        User user = User.builder()
                .memberId(userJoinDTO.getMemberId())
                .password(encodedPassword)
                .name(userJoinDTO.getName())
                .email(userJoinDTO.getEmail())
                .build();

        return userRepository.save(user) != null;
    }

    public User updateUser(ModifyDTO modifyDTO) {

        Optional<User> optionalUser = userRepository.findById(modifyDTO.getId());
        User user = null;
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
            String encodedPassword = SHA256HashingEncoder.encode(modifyDTO.getPassword());
            user = user.toBuilder()
                    .password(encodedPassword)
                    .email(modifyDTO.getEmail())
                    .name(modifyDTO.getName())
                    .profileImage(modifyDTO.getProfileImage())
                    .build();
            user = userRepository.save(user);
        }

        return user;
    }

    public boolean isDuplicateId(String memberId) {

        return userRepository.existsByMemberId(memberId);
    }

    public User userLogin(String memberId, String password) {

        String encodedPassword = SHA256HashingEncoder.encode(password);

        Optional<User> optionalUser = userRepository.findByMemberIdAndPassword(memberId, encodedPassword);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        }

        return null;
    }
}
