package com.devwork.weekend.like;

import com.devwork.weekend.like.service.LikeService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

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
        if(likeService.createLike(postId, loginUserDTO.getId())) {
            resuleMap.put("result", "success");
            return resuleMap;
        }

        resuleMap.put("result", "fail");
        return resuleMap;
    }

    @DeleteMapping("/like-remove-process")
    public Map<String, String> removeLike(@RequestParam long postId
                                          , HttpSession session) {
        Map<String, String> resuleMap = new HashMap<>();
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");

        if(likeService.deleteLike(postId, loginUserDTO.getId())) {
            resuleMap.put("result", "success");
            return resuleMap;
        }

        resuleMap.put("result", "fail");
        return resuleMap;
    }
}
