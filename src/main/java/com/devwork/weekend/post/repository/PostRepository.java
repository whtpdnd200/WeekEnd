package com.devwork.weekend.post.repository;

import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.PostListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p FROM Post p\n " +
            "JOIN FETCH p.user\n " +
            "ORDER BY p.createdAt DESC")
    public List<Post> findAllPost();
}
