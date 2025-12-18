package com.devwork.weekend.comment;

import com.devwork.weekend.comment.commentDTO.CommentListDTO;
import com.devwork.weekend.comment.commentDTO.ModifyCommentDTO;
import com.devwork.weekend.comment.commentDTO.SliceCommentDTO;
import com.devwork.weekend.comment.commentDTO.WriteCommentDTO;
import com.devwork.weekend.comment.service.CommentService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post/comment")
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping("/write-process")
    public Map<String, String> writeComment(@ModelAttribute WriteCommentDTO writeCommentDTO
                                            , HttpSession session) {

        Map<String, String> resultMap = new HashMap<>();
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");

        if(commentService.createComment(writeCommentDTO, loginUserDTO.getId())) {
            resultMap.put("result", "success");
            return resultMap;
        }
        resultMap.put("result", "fail");
        return resultMap;
    }

    @GetMapping("/comment-process")
    public Map<String, Object> getCommentList(@RequestParam long postId, Pageable pageable) {

        Map<String, Object> resultMap = new HashMap<>();
        SliceCommentDTO commentList = commentService.getComments(postId, pageable);
        if(commentList.getContent() != null) {
            resultMap.put("result", "success");
            resultMap.put("commentList", commentList);
            return  resultMap;
        }
        resultMap.put("result", "fail");
        return resultMap;
    }

    @GetMapping("/comment-next-process")
    public Map<String, Object> getCommentNextList(@RequestParam long postId,@RequestParam long lastId, Pageable pageable) {

        Map<String, Object> resultMap = new HashMap<>();
        SliceCommentDTO commentList = commentService.getNextComments(postId, lastId, pageable);
        if(commentList.getContent() != null) {
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
