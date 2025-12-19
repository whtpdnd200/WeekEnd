package com.devwork.weekend.comment.service;

import com.devwork.weekend.comment.commentDTO.CommentListDTO;
import com.devwork.weekend.comment.commentDTO.ModifyCommentDTO;
import com.devwork.weekend.comment.commentDTO.SliceCommentDTO;
import com.devwork.weekend.comment.commentDTO.WriteCommentDTO;
import com.devwork.weekend.comment.domain.Comment;
import com.devwork.weekend.comment.repository.CommentRepository;
import com.devwork.weekend.post.domain.Post;
import com.devwork.weekend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;


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

    public boolean deleteComment(long id) {

        Optional<Comment> optionalComment = commentRepository.findById(id);

        if(optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            commentRepository.delete(comment);

            return true;
        }
        return false;
    }

    public boolean updatedComment(ModifyCommentDTO modifyCommentDTO) {

        Optional<Comment> optionalComment = commentRepository.findById(modifyCommentDTO.getId());

        Comment comment = null;
        if(optionalComment.isPresent()) {
            comment = optionalComment.get();

            comment = comment.toBuilder()
                    .comment(modifyCommentDTO.getComment())
                    .build();

        }
        try {
            commentRepository.save(comment);
            return true;
        } catch(DataAccessException e) {
            return false;
        }
    }

    public List<CommentListDTO> getComment3(long postId) {

        List<CommentListDTO> commentList = new ArrayList<>();

        List<Comment> comments = commentRepository.findByPostIdLimit3(postId);

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

    public int getCommentCount(long postId) {

        return commentRepository.countCommentByPostId(postId);
    }


    public SliceCommentDTO getComments(long postId, Pageable pageable) {

        Slice<Comment> sliceComments = commentRepository.findByPostId(postId, PageRequest.of(0, 6));

        List<Comment> comments = sliceComments.getContent();

        long id = 0;
        if(comments.size() > 0) {
            id = comments.get(comments.size() - 1).getId();
        }


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

        SliceCommentDTO sliceCommentDTO = SliceCommentDTO.builder()
                .content(commentList)
                .hasNext(sliceComments.hasNext())
                .lastId(id)
                .build();

        return sliceCommentDTO;
    }

    public SliceCommentDTO getNextComments(long postId, long lastId, Pageable pageable) {

        Slice<Comment> sliceComments = commentRepository.findByNextPostId(postId, lastId, PageRequest.of(0, 5));

        List<Comment> comments = sliceComments.getContent();

        long id = 0;
        if(comments.size() > 0) {
            id = comments.get(comments.size() - 1).getId();
        }

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

        SliceCommentDTO sliceCommentDTO = SliceCommentDTO.builder()
                .content(commentList)
                .hasNext(sliceComments.hasNext())
                .lastId(id)
                .build();

        return sliceCommentDTO;
    }


//    public List<CommentListDTO> addList(List<Comment> commentList) {
//
//        List<CommentListDTO> comments = new ArrayList<>();
//        for(Comment comment : commentList) {
//            CommentListDTO commentListDTO = new CommentListDTO(
//                    comment.getId()
//                    , comment.getPost().getId()
//                    , comment.getUser().getId()
//                    , comment.getUser().getName()
//                    , comment.getUser().getProfileImage()
//                    , comment.getComment());
//
//            comments.add(commentListDTO);
//        }
//
//        return comments;
//    }
}
