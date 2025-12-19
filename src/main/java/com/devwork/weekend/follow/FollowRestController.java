package com.devwork.weekend.follow;

import com.devwork.weekend.follow.domain.Follow;
import com.devwork.weekend.follow.service.FollowService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post/follow")
public class FollowRestController {

    private final FollowService followService;

    @PostMapping("/follow-process")
    public Map<String, String> followAdd(@RequestParam long followId
                                        , HttpSession session) {

        Map<String, String> resultMap = new HashMap<>();
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        if(followService.createFollow(loginUserDTO.getId(), followId)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @DeleteMapping("/follow-remove-process")
    public Map<String, String> followRemove(@RequestParam long followId
                                            , HttpSession session) {

        Map<String, String> resultMap = new HashMap<>();
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        if(followService.followDelete(loginUserDTO.getId(), followId)) {
            resultMap.put("result", "success");
            return resultMap;
        }
        resultMap.put("result", "fail");
        return resultMap;
    }

}
