package com.devwork.weekend.user;

import com.devwork.weekend.post.service.PostService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import com.devwork.weekend.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {


    private final PostService postService;

    private final UserService userService;


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
    public String userInfo(Model model, Pageable pageable, @RequestParam("id") long userId, HttpSession session) {


        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        model.addAttribute("userInfo", userService.getUserInfo(userId, loginUserDTO.getId()));
        model.addAttribute("postList", userService.getUserPost(pageable, userId, loginUserDTO.getId()));
        return "weekend/user/user-info";
    }

    @GetMapping("/search")
    public String search() {

        return "weekend/user/search";
    }

}
