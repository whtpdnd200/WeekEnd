package com.devwork.weekend.post.service;

import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.WriteDTO;
import com.devwork.weekend.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {

        this.postRepository = postRepository;
    }

    public boolean createPost(WriteDTO writeDTO) {

        Post post = Post.builder()
                    .userId(writeDTO.getUserId())
                    .contents(writeDTO.getContents())
                    .imagePath(writeDTO.getImagePath())
                    .build();

        return postRepository.save(post) != null;
    }
}
