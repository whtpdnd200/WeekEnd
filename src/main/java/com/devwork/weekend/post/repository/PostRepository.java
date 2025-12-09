package com.devwork.weekend.post.repository;

import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.PostListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.id, p.user_id, u.name, p.contents, p.image_path, p.created_at, p.created_at\n" +
            "FROM `post` AS p\n" +
            "JOIN `user` AS u\n" +
            "ON p.user_id = u.id", nativeQuery = true)
    public List<PostListDTO> findAllPost();
}
