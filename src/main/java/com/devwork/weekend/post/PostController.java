package com.devwork.weekend.post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {

    private PostRestController postRestController;

    public PostController(PostRestController postRestController) {
        this.postRestController = postRestController;
    }

    @GetMapping("/list")
    public String list(Model model) {

        model.addAttribute("postList", postRestController.getPostList());
        return "weekend/post/list";
    }

    @GetMapping("/write")
    public String write() {

        return "weekend/post/write";
    }
}
