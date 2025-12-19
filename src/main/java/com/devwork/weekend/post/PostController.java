package com.devwork.weekend.post;

import com.devwork.weekend.post.service.PostService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
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
    public String list(Model model, Pageable pageable
                        , HttpSession session) {

        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        model.addAttribute("postList", postService.getPostList(pageable, loginUserDTO.getId()));
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

    @GetMapping("/like-list")
    public String likeList(Pageable pageable, HttpSession session, Model model) {

        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        model.addAttribute("postList", postService.getPostListByLike(pageable, loginUserDTO.getId()));
        return "weekend/post/like-list";
    }
}
