package com.devwork.weekend.post.repository;

import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.PostListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(""" 
            SELECT p FROM Post p
            JOIN FETCH p.user
            ORDER BY p.createdAt DESC
            """)
    public List<Post> findAllPost(Pageable pageable);

    @Query(""" 
            SELECT p FROM Post p
            JOIN FETCH p.user
            ORDER BY p.createdAt DESC
            """)
    public Slice<Post> selectAllPost(Pageable pageable);
}
