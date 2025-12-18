package com.devwork.weekend.like;

import com.devwork.weekend.like.service.LikeService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/post/like")
public class LikeRestController {

    private final LikeService likeService;

    public LikeRestController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like-process")
    public Map<String, String> likeAdd(@RequestParam long postId
                                      , HttpSession session)
    {
        Map<String, String> resuleMap = new HashMap<>();
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        long userId = loginUserDTO.getId();
        if(likeService.createLike(postId, userId)) {
            resuleMap.put("result", "success");
            return resuleMap;
        }

        resuleMap.put("result", "fail");
        return resuleMap;
    }
}
