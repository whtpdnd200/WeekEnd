package com.devwork.weekend.comment.service;

import com.devwork.weekend.comment.commentDTO.WriteCommentDTO;
import com.devwork.weekend.comment.domain.Comment;
import com.devwork.weekend.comment.repository.CommentRepository;
import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public boolean createComment(WriteCommentDTO writeCommentDTO, long id) {

        User user = User.builder()
                    .id(id)
                    .build();

        Post post = Post.builder()
                    .id(writeCommentDTO.getPostId())
                    .build();

        Comment comment = Comment.builder()
                    .user(user)
                    .post(post)
                    .comment(writeCommentDTO.getComment())
                    .build();
        return commentRepository.save(comment) != null;
    }
}
