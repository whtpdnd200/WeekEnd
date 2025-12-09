package com.devwork.weekend.user;

import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import com.devwork.weekend.user.UserDTO.UserModifyDTO;
import com.devwork.weekend.user.UserDTO.UserJoinDTO;
import com.devwork.weekend.user.domain.User;
import com.devwork.weekend.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join-process")
    public Map<String, String> join(@ModelAttribute UserJoinDTO userJoinDTO) {

        Map<String, String> resultMap = new HashMap<>();

        if(userService.createUser(userJoinDTO)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @GetMapping("/duplicate-check")
    public Map<String, Boolean> isDuplicate(String memberId) {

        Map<String, Boolean> resultMap = new HashMap<>();

        resultMap.put("isDuplicate", userService.isDuplicateId(memberId));

        return resultMap;
    }

    @PostMapping("/login-process")
    public Map<String, String> userLogin(@RequestParam String memberId
                                        , @RequestParam String password
                                        , HttpSession session) {

        Map<String, String> resultMap = new HashMap<>();

        LoginUserDTO loginUserDTO = userService.userLogin(memberId, password);

        if(loginUserDTO != null) {

            resultMap.put("result", "success");
            session.setAttribute("userInfo", loginUserDTO);
            return resultMap;
        }

        resultMap.put("result", "fail");

        return resultMap;
    }

    @PutMapping("/modify-process")
    public Map<String, String> modify(@RequestBody UserModifyDTO modifyDTO
                                      , HttpSession session) {

        Map<String, String> resultMap = new HashMap<>();
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        long id = loginUserDTO.getId();
        LoginUserDTO user = userService.updateUser(modifyDTO, id);
        if(user != null) {

            resultMap.put("result", "success");

            session.setAttribute("userInfo", user);
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }
}
