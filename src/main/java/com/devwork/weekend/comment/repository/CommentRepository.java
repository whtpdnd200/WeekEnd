package com.devwork.weekend.comment.repository;

import com.devwork.weekend.comment.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Query("""
        SELECT c FROM Comment c
        JOIN FETCH c.user
        WHERE c.post.id = :postId
        ORDER BY c.id DESC
        """)
    public Slice<Comment> findByPostId(@Param("postId") long postId, Pageable pageable);


    @Query("""
        SELECT c FROM Comment c
        JOIN FETCH c.user
        WHERE c.post.id = :postId
        AND c.id < :lastId
        ORDER BY c.id DESC
        """)
    public Slice<Comment> findByNextPostId(@Param("postId") long postId, @Param("lastId") long lastId, Pageable pageable);

    @Query("""
            SELECT c FROM Comment c
            JOIN FETCH c.user
            WHERE c.post.id = :postId
            ORDER BY c.createdAt DESC
            LIMIT 3
            """)
    public List<Comment> findByPostIdLimit3(@Param("postId") long postId);

    @Query("""
            SELECT COUNT(c)
            FROM Comment c
            WHERE c.post.id = :postId
            """)
    public int countCommentByPostId(@Param("postId") long postId);
}
