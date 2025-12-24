package com.devwork.weekend.passwordReset;

import com.devwork.weekend.passwordReset.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/password-reset")
public class PasswordRestRestController {

    private final PasswordResetService passwordResetService;


    @GetMapping("/send-email")
    public Map<String, String> sendEmail(@RequestParam String memberId
                                        , @RequestParam String email) {

        Map<String, String> resultMap = new HashMap<>();

        if(passwordResetService.sendMailAndInsert(memberId, email)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @PutMapping("/password-change-process")
    public Map<String, String> passwordReset(@RequestParam String password, @RequestParam String code, @RequestParam String email) {

        Map<String, String> resultMap = new HashMap<>();

        if(passwordResetService.updatePasswordReset(code, email, password)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }
}
