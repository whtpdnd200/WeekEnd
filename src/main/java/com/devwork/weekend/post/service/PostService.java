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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    
    private final CommentService commentService;

    public PostService(PostRepository postRepository, CommentService commentService) {

        this.postRepository = postRepository;
        this.commentService = commentService;
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

        List<Post> posts = postRepository.findAllPost();
        List<PostListDTO> postList = new ArrayList<>();

        for(Post post : posts) {
            List<CommentListDTO> comments = commentService.addList(post.getCommentList());

            PostListDTO postListDTO = new PostListDTO(post.getId()
                                                    , post.getUser().getId()
                                                    , post.getUser().getName()
                                                    , post.getUser().getProfileImage()
                                                    , post.getContents()
                                                    , post.getImagePath()
                                                    , comments
                                                    , post.getCreatedAt()
                                                    , post.getUpdatedAt());
            postList.add(postListDTO);
        }
        return postList;
    }

    public boolean updatePost(PostModifyDTO postModifyDTO) {

        Optional<Post> optionalPost = postRepository.findById(postModifyDTO.getId());

        Post post = null;
        if(optionalPost.isPresent()) {
            post = optionalPost.get();

            post = post.toBuilder()
                    .contents(postModifyDTO.getContents())
                    .imagePath(postModifyDTO.getImagePath())
                    .build();

            post = postRepository.save(post);
        }
        return post != null;
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
