package com.devwork.weekend.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {


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
    public String userInfo() {

        return "weekend/user/user-info";
    }
}
