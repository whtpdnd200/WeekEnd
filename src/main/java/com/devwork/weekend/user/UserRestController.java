package com.devwork.weekend.user;

import com.devwork.weekend.user.UserDTO.UserJoinDTO;
import com.devwork.weekend.user.service.UserService;
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
    public Map<String, String> join(@ModelAttribute UserJoinDTO dto) {

        Map<String, String> resultMap = new HashMap<>();

        if(userService.create(dto)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @GetMapping("/duplicate-check")
    public Map<String, Boolean> isDuplicate(String id) {
        Map<String, Boolean> resultMap = new HashMap<>();

        resultMap.put("isDuplicate", userService.isDuplicateId(id));
        return resultMap;
    }

    @PostMapping("/login-process")
    public Map<String, String> userLogin(@RequestParam String memberId, @RequestParam String password) {

        Map<String, String> resultMap = new HashMap<>();
        if(userService.userLogin(memberId, password)) {
            resultMap.put("result", "success");
            return resultMap;
        }
        resultMap.put("result", "fail");
        return resultMap;
    }
}
