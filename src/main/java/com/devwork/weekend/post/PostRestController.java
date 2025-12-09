package com.devwork.weekend.post;

import com.devwork.weekend.post.postDTO.WriteDTO;
import com.devwork.weekend.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostRestController {

    private PostService postService;

    public PostRestController(PostService postService) {

        this.postService = postService;
    }

    @PostMapping("/write-process")
    public Map<String, String> write(@ModelAttribute WriteDTO writeDTO
                                        , HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        Map<String, String> resultMap = new HashMap<>();
        if(postService.createPost(writeDTO)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");

        return resultMap;
    }
}
