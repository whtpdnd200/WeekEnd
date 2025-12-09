package com.devwork.weekend.post.service;

import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.PostListDTO;
import com.devwork.weekend.post.postDTO.WriteDTO;
import com.devwork.weekend.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {

        this.postRepository = postRepository;
    }

    public boolean createPost(WriteDTO writeDTO, long id) {


        Post post = Post.builder()
                    .userId(id)
                    .contents(writeDTO.getContents())
                    .imagePath(writeDTO.getImagePath())
                    .build();

        return postRepository.save(post) != null;
    }

    public List<PostListDTO> getPostList() {

        return postRepository.findAllPost();
    }
}
