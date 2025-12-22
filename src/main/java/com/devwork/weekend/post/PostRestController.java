package com.devwork.weekend.post;

import com.devwork.weekend.post.postDTO.PostModifyDTO;
import com.devwork.weekend.post.postDTO.SlicePostDTO;
import com.devwork.weekend.post.postDTO.WriteDTO;
import com.devwork.weekend.post.service.PostService;
import com.devwork.weekend.user.UserDTO.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


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

    @GetMapping("/list-process")
    public SlicePostDTO getPostList(Pageable pageable
            , HttpSession session) {

        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");

        return postService.getPostList(pageable, loginUserDTO.getId());
    }

    @GetMapping("/nextPostList-process")
    public SlicePostDTO nextPostList(Pageable pageable, @RequestParam long lastId
                                    , HttpSession session) {

        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");

        return postService.getNextPostList(pageable, lastId, loginUserDTO.getId());
    }

    @GetMapping("/follow-list-process")
    public SlicePostDTO getPostListByFollow(Pageable pageable, HttpSession session) {
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");

        return postService.getPostListByFollow(pageable, loginUserDTO.getId());
    }

    @GetMapping("/follow-next-process")
    public SlicePostDTO getNextPostListByFollow(Pageable pageable, @RequestParam long lastId, HttpSession session) {
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");

        return postService.getNextPostListByFollow(pageable, lastId, loginUserDTO.getId());
    }

    @GetMapping("/like-process")
    public SlicePostDTO getNextPostListByLike(Pageable pageable, HttpSession session) {
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        return postService.getPostListByLike(pageable, loginUserDTO.getId());
    }

    @GetMapping("/like-next-process")
    public SlicePostDTO getNextPostListByLike(Pageable pageable, @RequestParam long lastId, HttpSession session) {
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
        return postService.getNextPostListByLike(pageable, lastId, loginUserDTO.getId());
    }

    @GetMapping("/search-process")
    public Map<String, Object> getSearchList(Pageable pageable
                                            , @RequestParam String keyword
                                            , HttpSession session) {

        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");

        Map<String, Object> resultMap = new HashMap<>();

        SlicePostDTO slicePostDTO = postService.getPostListByKeyword(pageable, keyword, loginUserDTO.getId());

        if(slicePostDTO != null) {
            resultMap.put("result", "success");
            resultMap.put("postList", slicePostDTO);
            return resultMap;
        }

        resultMap.put("result", "fail");
        return resultMap;
    }

    @GetMapping("/search-next-process")
    public SlicePostDTO getNextSearchList(Pageable pageable
                                                , @RequestParam long lastId
                                                , @RequestParam String keyword
                                                , HttpSession session) {

        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");



        return postService.getNextPostListByKeyword(pageable, keyword, loginUserDTO.getId(), lastId);

    }


//    @GetMapping("/search-next-process")
//    public Map<String, Object> getNextSearchList(Pageable pageable
//            , @RequestParam long lastId
//            , @RequestParam String keyword
//            , HttpSession session) {
//
//        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("userInfo");
//
//        Map<String, Object> resultMap = new HashMap<>();
//
//        SlicePostDTO slicePostDTO = postService.getNextPostListByKeyword(pageable, keyword, loginUserDTO.getId(), lastId);
//
//        if(slicePostDTO != null) {
//            resultMap.put("result", "success");
//            resultMap.put("postList", slicePostDTO);
//            return resultMap;
//        }
//
//        resultMap.put("result", "fail");
//        return resultMap;
//
//    }


}
