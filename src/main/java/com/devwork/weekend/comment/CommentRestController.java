package com.devwork.weekend.comment;

import com.devwork.weekend.comment.commentDTO.CommentListDTO;
import com.devwork.weekend.comment.commentDTO.ModifyCommentDTO;
import com.devwork.weekend.comment.commentDTO.WriteCommentDTO;
import com.devwork.weekend.comment.service.CommentService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post/comment")
public class CommentRestController {

    private final CommentService commentService;

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

    @GetMapping("/comment-process")
    public Map<String, Object> getCommentList(@RequestParam long postId) {

        Map<String, Object> resultMap = new HashMap<>();
        List<CommentListDTO> commentList = commentService.getComments(postId);
        if(commentList != null) {
            resultMap.put("result", "success");
            resultMap.put("commentList", commentList);
            return  resultMap;
        }
        resultMap.put("result", "fail");
        return resultMap;
    }

    @DeleteMapping("/remove-process")
    public Map<String, String> removeComment(@RequestParam long commentId) {

        Map<String, String> resultMap = new HashMap<>();

        if(commentService.deleteComment(commentId)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @PutMapping("/modify-process")
    public Map<String, String> modifyComment(@RequestBody ModifyCommentDTO modifyCommentDTO) {

        Map<String, String> resultMap = new HashMap<>();

        if(commentService.updatedComment(modifyCommentDTO)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }
}
