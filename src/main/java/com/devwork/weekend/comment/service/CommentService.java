package com.devwork.weekend.comment.service;

import com.devwork.weekend.comment.commentDTO.CommentListDTO;
import com.devwork.weekend.comment.commentDTO.WriteCommentDTO;
import com.devwork.weekend.comment.domain.Comment;
import com.devwork.weekend.comment.repository.CommentRepository;
import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.user.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

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
        try {
            commentRepository.save(comment);
        } catch(DataAccessException e) {
            return false;
        }
        return true;
    }

    public List<CommentListDTO> addList(List<Comment> commentList) {

        List<CommentListDTO> comments = new ArrayList<>();
        for(Comment comment : commentList) {
            CommentListDTO commentListDTO = new CommentListDTO(
                                                comment.getId()
                                                , comment.getPost().getId()
                                                , comment.getUser().getId()
                                                , comment.getUser().getName()
                                                , comment.getUser().getProfileImage()
                                                , comment.getComment());

            comments.add(commentListDTO);
            if(comments.size() > 2) {
                break;
            }
        }

        return comments;
    }

    public List<CommentListDTO> getComments(long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);

        List<CommentListDTO> commentList = new ArrayList<>();

        for(Comment comment : comments) {

            CommentListDTO commentListDTO = new CommentListDTO(
                                                comment.getId()
                                                , comment.getPost().getId()
                                                , comment.getUser().getId()
                                                , comment.getUser().getName()
                                                , comment.getUser().getProfileImage()
                                                , comment.getComment());

            commentList.add(commentListDTO);
        }
        return commentList;
    }
}
