package com.devwork.weekend.user;

import com.devwork.weekend.post.service.PostService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/user")
public class UserController {


    private final PostService postService;

    public UserController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/join")
    public String join() {

        return "weekend/user/join";
    }

    @GetMapping("/login")
    public String login() {
        return "weekend/user/login";
    }

    @GetMapping("/modify")
    public String modify() {

        return "weekend/user/modify";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession();

        session.invalidate();

        return "redirect:/user/login";
    }

    @GetMapping("/info")
    public String userInfo(Model model, Pageable pageable, HttpSession session) {

        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        model.addAttribute("postList", postService.getPostList(pageable, loginUserDTO.getId()));
        return "weekend/user/user-info";
    }

}
