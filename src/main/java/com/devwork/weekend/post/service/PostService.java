package com.devwork.weekend.post.service;

import com.devwork.weekend.comment.commentDTO.CommentListDTO;
import com.devwork.weekend.comment.service.CommentService;
import com.devwork.weekend.common.FileManager;
import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.PostDTO;
import com.devwork.weekend.post.postDTO.PostListDTO;
import com.devwork.weekend.post.postDTO.PostModifyDTO;
import com.devwork.weekend.post.postDTO.WriteDTO;
import com.devwork.weekend.post.repository.PostRepository;
import com.devwork.weekend.user.domain.User;
import com.devwork.weekend.user.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {

        this.postRepository = postRepository;
        this.userService = userService;
    }

    public boolean createPost(WriteDTO writeDTO, long id) {


        String imagePath = FileManager.savaFile(id, writeDTO.getImagePath());

        User user = User.builder()
                        .id(id)
                        .build();

        Post post = Post.builder()
                    .user(user)
                    .contents(writeDTO.getContents())
                    .imagePath(imagePath)
                    .build();
        try {
            postRepository.save(post);
        } catch(DataAccessException e) {
            return false;
        }
        return true;
    }


    public List<PostListDTO> getPostList() {

        List<Post> posts = postRepository.findAll(Sort.by("id").descending());

        List<PostListDTO> postList = new ArrayList<>();

        for(Post post : posts) {

            // Post -> PostDetail
            // 1 + N 문제  : cache
            User user = userService.getUser(post.getUser().getId());

            PostListDTO postListDTO = PostListDTO.builder()
                    .id(post.getId())
                    .userId(post.getUser().getId())
                    .name(post.getUser().getName())
                    .profileImage(post.getUser().getProfileImage())
                    .contents(post.getContents())
                    .imagePath(post.getImagePath())
                    .build();

            postList.add(postListDTO);
        }

        return postList;
    }

    public boolean updatePost(PostModifyDTO postModifyDTO, long id) {

        Optional<Post> optionalPost = postRepository.findById(postModifyDTO.getId());

        String imagePath = FileManager.savaFile(id, postModifyDTO.getImagePath());
        Post post = null;
        if(optionalPost.isPresent()) {
            post = optionalPost.get();

            if(imagePath != null && post.getImagePath() != null) {
                FileManager.deleteFile(post.getImagePath());
                
            }

            if(imagePath == null) {
                imagePath = post.getImagePath();
            }

            post = post.toBuilder()
                    .contents(postModifyDTO.getContents())
                    .imagePath(imagePath)
                    .build();
        }
        try{
            postRepository.save(post);
            return true;
        } catch(DataAccessException e) {
            return false;
        }
    }

    public boolean deletePost(long id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            postRepository.delete(post);
            return true;
        }

        return false;
    }

    public PostDTO getPost(long id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        PostDTO postDTO = new PostDTO();

        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            postDTO.setId(post.getId());
            postDTO.setUserId(post.getUser().getId());
            postDTO.setContents(post.getContents());
            postDTO.setImagePath(post.getImagePath());
        }
        return postDTO;
    }
}
