package com.devwork.weekend.post;

import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.PostListDTO;
import com.devwork.weekend.post.postDTO.PostModifyDTO;
import com.devwork.weekend.post.postDTO.SlicePostDTO;
import com.devwork.weekend.post.postDTO.WriteDTO;
import com.devwork.weekend.post.service.PostService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/post")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {

        this.postService = postService;
    }

    @PostMapping("/write-process")
    public Map<String, String> writePost(@ModelAttribute WriteDTO writeDTO
                                         , @RequestPart(value = "imageFile", required = false) MultipartFile file
                                    , HttpSession session) {

        if(file != null) {
            writeDTO.setImagePath(file);
        }
        Map<String, String> resultMap = new HashMap<>();
        LoginUserDTO loginUserDTO = (LoginUserDTO) session.getAttribute("userInfo");
        long id = loginUserDTO.getId();
        if(postService.createPost(writeDTO, id)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");

        return resultMap;
    }

    @GetMapping("/list-process")
    public List<PostListDTO> getPostList(Pageable pageable) {


        return postService.getPostList(pageable);
    }

    @DeleteMapping("/remove-process")
    public Map<String, String> removePost(@RequestParam long postId) {
        Map<String, String> resultMap = new HashMap<>();

        if(postService.deletePost(postId)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @PutMapping("/modify-process")
    public Map<String, String> modifyPost(@ModelAttribute PostModifyDTO postModifyDTO
                                            , @RequestPart(value = "imageFile", required = false) MultipartFile file
                                          , HttpSession session) {

        Map<String, String> resultMap = new HashMap<>();
        if(file != null) {
            postModifyDTO.setImagePath(file);
        }
        LoginUserDTO loginUserDTO = (LoginUserDTO) session.getAttribute("userInfo");
        long id = loginUserDTO.getId();

        if(postService.updatePost(postModifyDTO, id)) {
            resultMap.put("result", "success");
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @GetMapping("/test")
    public SlicePostDTO test(@PageableDefault(size = 5, sort = "id", direction = DESC) Pageable pageable) {

        return postService.selectPost(pageable);
    }
}
