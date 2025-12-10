package com.devwork.weekend.post;

import com.devwork.weekend.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public String list(Model model) {

        model.addAttribute("postList", postService.getPostList());
        return "weekend/post/list";
    }

    @GetMapping("/write")
    public String write() {

        return "weekend/post/write";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam long postId, Model model) {

        model.addAttribute("post", postService.getPost(postId));
        return "weekend/post/modify";
    }
}
