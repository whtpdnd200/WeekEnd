package com.devwork.weekend.user.service;

import com.devwork.weekend.common.FileManager;
import com.devwork.weekend.follow.service.FollowService;
import com.devwork.weekend.passwordReset.service.PasswordResetService;
import com.devwork.weekend.post.postDTO.SlicePostDTO;
import com.devwork.weekend.post.service.PostService;
import com.devwork.weekend.user.UserDTO.*;
import com.devwork.weekend.common.SHA256HashingEncoder;
import com.devwork.weekend.user.repository.UserRepository;
import com.devwork.weekend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowService followService;
    private final PostService postService;

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

        try {
            userRepository.save(user);
        } catch(DataAccessException e) {
            return false;
        }
        return true;
    }

    public LoginUserDTO updateUser(UserModifyDTO modifyDTO, long id) {

        String imagePath = FileManager.savaFile(id, modifyDTO.getProfileImage());
        
        Optional<User> optionalUser = userRepository.findById(id);
        User user = null;
        LoginUserDTO loginUserDTO = null;
        if(optionalUser.isPresent()) {
            user = optionalUser.get();

            if(imagePath != null && user.getProfileImage() != null) {
                FileManager.deleteFile(user.getProfileImage());

            }

            if(imagePath == null) {
                imagePath = user.getProfileImage();
            }

            
            String encodedPassword = SHA256HashingEncoder.encode(modifyDTO.getPassword());
            user = user.toBuilder()
                    .password(encodedPassword)
                    .email(modifyDTO.getEmail())
                    .name(modifyDTO.getName())
                    .profileImage(imagePath)
                    .build();
            user = userRepository.save(user);

        }
        if(user != null) {

            loginUserDTO = new LoginUserDTO(user.getId()
                    , user.getMemberId()
                    , user.getName()
                    , user.getEmail()
                    , user.getProfileImage());
        }
        return loginUserDTO;
    }


    public boolean isDuplicateId(String memberId) {

        return userRepository.existsByMemberId(memberId);
    }

    public LoginUserDTO userLogin(String memberId, String password) {

        String encodedPassword = SHA256HashingEncoder.encode(password);

        Optional<User> optionalUser = userRepository.findByMemberIdAndPassword(memberId, encodedPassword);
        LoginUserDTO loginUserDTO = null;
        if(optionalUser.isPresent()) {

            User user = optionalUser.get();
            loginUserDTO = new LoginUserDTO(user.getId()
                    , user.getMemberId()
                    , user.getName()
                    , user.getEmail()
                    , user.getProfileImage());

            return loginUserDTO;
        }

        return null;
    }

    public UserMemberIdDTO getUserMemberIdByEmail(String email) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();

            UserMemberIdDTO userMemberIdDTO = UserMemberIdDTO.builder()
                    .memberId(user.getMemberId())
                    .build();

            return userMemberIdDTO;
        }

        return null;
    }

    public boolean existsByMemberIdAndEmail(String memberId, String email) {

        return userRepository.existsByMemberIdAndEmail(memberId, email);
    }

    public boolean updatePassword(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {

            User user = optionalUser.get();
            String encodedPassword = SHA256HashingEncoder.encode(password);
            user = user.toBuilder()
                    .password(encodedPassword)
                    .build();

            try {
                userRepository.save(user);
            } catch(DataAccessException e) {
                return false;
            }

            return true;
        }

        return false;
    }

    public UserInfoDTO getUserInfo(long userId, long loginId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();

            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .profileImage(user.getProfileImage())
                    .followCountDTO(followService.getCounts(userId))
                    .isFollow(followService.isFollow(loginId, userId

                    ))
                    .isFollower(followService.isFollow(userId, loginId))
                    .build();
            return userInfoDTO;
        }
        return null;
    }

    public SlicePostDTO getUserPost(Pageable pageable, long userId, long loginId) {
        return postService.getPostListByUserId(pageable, userId, loginId);
    }

    public SlicePostDTO getUserPostNext(Pageable pageable, long userId, long lastId, long loginId) {
        return postService.getPostNextListByUserId(pageable, userId, lastId, loginId);
    }

    public List<UserBasicDTO> getFollowingList(long userId, long loginId) {

        List<User> followings = userRepository.findAllByFollowingList(followService.getFollowingList(userId));

        List<UserBasicDTO> followingList = new ArrayList<>();

        for(User user : followings) {

            UserBasicDTO userBasicDTO = UserBasicDTO.builder()
                    .id(user.getId())
                    .memberId(user.getMemberId())
                    .name(user.getName())
                    .profilePath(user.getProfileImage())
                    .isFollow(followService.isFollow(loginId, user.getId()))
                    .isFollower(followService.isFollow(user.getId(), loginId))
                    .build();
            followingList.add(userBasicDTO);
        }
        return followingList;
    }

    public List<UserBasicDTO> getFollowerList(long userId, long loginId) {

        List<User> followers = userRepository.findAllByFollowerList(followService.getFollowerList(userId));

        List<UserBasicDTO> followerList = new ArrayList<>();

        for(User user : followers) {

            UserBasicDTO userBasicDTO = UserBasicDTO.builder()
                    .id(user.getId())
                    .memberId(user.getMemberId())
                    .name(user.getName())
                    .profilePath(user.getProfileImage())
                    .isFollow(followService.isFollow(loginId, user.getId()))
                    .isFollower(followService.isFollow(user.getId(), loginId))
                    .build();
            followerList.add(userBasicDTO);
        }
        return followerList;
    }
}
