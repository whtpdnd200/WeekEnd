package com.devwork.weekend.post.service;

import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.PostListDTO;
import com.devwork.weekend.post.postDTO.WriteDTO;
import com.devwork.weekend.post.repository.PostRepository;
import com.devwork.weekend.user.domain.User;
import com.devwork.weekend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository
                      , UserRepository userRepository) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public boolean createPost(WriteDTO writeDTO, long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        User user = null;
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        Post post = Post.builder()
                    .user(user)
                    .contents(writeDTO.getContents())
                    .imagePath(writeDTO.getImagePath())
                    .build();

        return postRepository.save(post) != null;
    }

    public List<PostListDTO> getPostList() {

        List<Post> posts = postRepository.findAllPost();
        List<PostListDTO> postList = new ArrayList<>();
        for(Post post : posts) {
            PostListDTO postListDTO = new PostListDTO(post.getId()
                                                    , post.getUser().getId()
                                                    , post.getUser().getName()
                                                    , post.getUser().getProfileImage()
                                                    , post.getContents()
                                                    , post.getImagePath()
                                                    , post.getCreatedAt()
                                                    , post.getUpdatedAt());
            postList.add(postListDTO);
        }
        return postList;
    }
}
