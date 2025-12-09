package com.devwork.weekend.comment;

import com.devwork.weekend.comment.commentDTO.WriteCommentDTO;
import com.devwork.weekend.comment.service.CommentService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/post/comment")
public class CommentRestController {

    private CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/write-process")
    public Map<String, String> writeComment(@ModelAttribute WriteCommentDTO writeCommentDTO
                                            , HttpSession session) {

        Map<String, String> resultMap = new HashMap<>();
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        long id = loginUserDTO.getId();

        if(commentService.createComment(writeCommentDTO, id)) {
            resultMap.put("result", "success");
            return resultMap;
        }
        resultMap.put("result", "fail");
        return resultMap;
    }
}
