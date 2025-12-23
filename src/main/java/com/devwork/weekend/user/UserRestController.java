package com.devwork.weekend.user;

import com.devwork.weekend.post.postDTO.SlicePostDTO;
import com.devwork.weekend.user.UserDTO.*;
import com.devwork.weekend.user.domain.User;
import com.devwork.weekend.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join-process")
    public Map<String, String> join(@ModelAttribute UserJoinDTO userJoinDTO) {

        Map<String, String> resultMap = new HashMap<>();

        if(userService.createUser(userJoinDTO)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @GetMapping("/duplicate-check")
    public Map<String, Boolean> isDuplicate(String memberId) {

        Map<String, Boolean> resultMap = new HashMap<>();

        resultMap.put("isDuplicate", userService.isDuplicateId(memberId));

        return resultMap;
    }

    @PostMapping("/login-process")
    public Map<String, String> userLogin(@RequestParam String memberId
                                        , @RequestParam String password
                                        , HttpSession session) {

        Map<String, String> resultMap = new HashMap<>();

        LoginUserDTO loginUserDTO = userService.userLogin(memberId, password);

        if(loginUserDTO != null) {

            resultMap.put("result", "success");
            session.setAttribute("userInfo", loginUserDTO);
            return resultMap;
        }

        resultMap.put("result", "fail");

        return resultMap;
    }

    @PutMapping("/modify-process")
    public Map<String, String> modify(@ModelAttribute UserModifyDTO modifyDTO
                                      , @RequestPart(value = "image", required = false) MultipartFile file
                                      , HttpSession session) {


        if(file != null) {
            modifyDTO.setProfileImage(file);
        }

        Map<String, String> resultMap = new HashMap<>();
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        long id = loginUserDTO.getId();
        LoginUserDTO user = userService.updateUser(modifyDTO, id);
        if(user != null) {

            resultMap.put("result", "success");

            session.setAttribute("userInfo", user);
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @GetMapping("/info-next-process")
    public SlicePostDTO getNextPost(Pageable pageable, @RequestParam long userInfoId, @RequestParam long lastId, HttpSession session) {

        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        return userService.getUserPostNext(pageable, userInfoId, lastId, loginUserDTO.getId());
    }

    @GetMapping("/following-list")
    public List<UserBasicDTO> getFollowingList(@RequestParam long userId
                                               , HttpSession session) {

        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        return userService.getFollowingList(userId, loginUserDTO.getId());
    }

    @GetMapping("/follower-list")
    public List<UserBasicDTO> getFollowerList(@RequestParam long userId
                                              , HttpSession session) {

        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        return userService.getFollowerList(userId, loginUserDTO.getId());
    }

    @GetMapping("/search-process")
    public Map<String, Object> search(@RequestParam String email) {

        Map<String, Object> resultMap = new HashMap<>();

        UserMemberIdDTO userMemberIdDTO = userService.getUserMemberIdByEmail(email);

        if(userMemberIdDTO != null) {
            resultMap.put("result", "success");
            resultMap.put("id", userMemberIdDTO.getMemberId());
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }
}
