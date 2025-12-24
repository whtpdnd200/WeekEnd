package com.devwork.weekend.user;

import com.devwork.weekend.passwordReset.PasswordResetDTO.PasswordResetBasicDTO;
import com.devwork.weekend.passwordReset.service.PasswordResetService;
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



    private final UserService userService;
    private final PasswordResetService passwordResetService;

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

    @GetMapping("/password-change")
    public String passwordChange(@RequestParam String code, Model model) {

        PasswordResetBasicDTO passwordResetBasicDTO = passwordResetService.getCodeInfo(code);
        model.addAttribute("email", passwordResetBasicDTO.getEmail());
        model.addAttribute("isValidation", passwordResetService.isValidation(passwordResetBasicDTO));
        return "weekend/user/password-change";
    }

}
