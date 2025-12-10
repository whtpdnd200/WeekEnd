package com.devwork.weekend.post;

import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.PostListDTO;
import com.devwork.weekend.post.postDTO.PostModifyDTO;
import com.devwork.weekend.post.postDTO.WriteDTO;
import com.devwork.weekend.post.service.PostService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {

        this.postService = postService;
    }

    @PostMapping("/write-process")
    public Map<String, String> writePost(@ModelAttribute WriteDTO writeDTO
                                    , HttpSession session) {

        Map<String, String> resultMap = new HashMap<>();

        LoginUserDTO loginUserDTO = (LoginUserDTO) session.getAttribute("userInfo");
        long id = loginUserDTO.getId();
        if(postService.createPost(writeDTO, id)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");

        return resultMap;
    }

    @GetMapping("/list-process")
    public List<PostListDTO> getPostList() {

        return postService.getPostList();
    }

    @DeleteMapping("/remove-process")
    public Map<String, String> removePost(@RequestParam long postId) {
        Map<String, String> resultMap = new HashMap<>();

        if(postService.deletePost(postId)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @PutMapping("/modify-process")
    public Map<String, String> modifyPost(@RequestBody PostModifyDTO postModifyDTO) {

        Map<String, String> resultMap = new HashMap<>();

        if(postService.updatePost(postModifyDTO)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }
}
