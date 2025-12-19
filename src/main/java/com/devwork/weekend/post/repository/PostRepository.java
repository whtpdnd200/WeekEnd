package com.devwork.weekend.post.repository;

import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.post.postDTO.PostListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(""" 
            SELECT p FROM Post p
            JOIN FETCH p.user
            ORDER BY p.createdAt DESC
            """)
    public Slice<Post> findAllPost(Pageable pageable);

    @Query(""" 
            SELECT p FROM Post p
            JOIN FETCH p.user
            WHERE p.id < :lastId
            ORDER BY p.createdAt DESC
            """)
    public Slice<Post> findAllNextPost(Pageable pageable, @Param("lastId") long lastId);

    @Query(""" 
            SELECT p FROM Post p
            JOIN FETCH p.user
            WHERE p.user.id IN(:followList)
            ORDER BY p.createdAt DESC
            """)
    public Slice<Post> findAllPostByFollow(Pageable pageable, @Param("followList") List<Long> followList);

    @Query(""" 
            SELECT p FROM Post p
            JOIN FETCH p.user
            WHERE p.id < :lastId AND p.user.id IN(:followList) 
            ORDER BY p.createdAt DESC
            """)
    public Slice<Post> findAllNextPostByFollow(Pageable pageable, @Param("followList") List<Long> followList, @Param("lastId") long lastId);

    @Query(""" 
            SELECT p FROM Post p
            JOIN FETCH p.user
            WHERE p.id IN(:postIdList)
            ORDER BY p.createdAt DESC
            """)
    public Slice<Post> findALlPostByLike(Pageable pageable, @Param("postIdList") List<Long> postIdList);


    @Query(""" 
            SELECT p FROM Post p
            JOIN FETCH p.user
            WHERE p.id < :lastId AND p.id IN(:postIdList)
            ORDER BY p.createdAt DESC
            """)
    public Slice<Post> findALlNextPostByLike(Pageable pageable, @Param("postIdList") List<Long> postIdList, @Param("lastId") long lastId);


    @Query("""
        SELECT p FROM Post p
        JOIN FETCH p.user
        WHERE p.contents LIKE CONCAT('%', :keyword, '%')
        ORDER BY p.createdAt DESC
        """)
    public Slice<Post> findAllPostByKeyword(Pageable pageable, @Param("keyword") String keyword);

    @Query("""
        SELECT p FROM Post p
        JOIN FETCH p.user
        WHERE p.id < :lastId AND p.contents LIKE CONCAT('%', :keyword, '%')
        ORDER BY p.createdAt DESC
        """)
    public Slice<Post> findAllNextPostByKeyword(Pageable pageable, @Param("keyword") String keyword, @Param("lastId") long lastId);
}
