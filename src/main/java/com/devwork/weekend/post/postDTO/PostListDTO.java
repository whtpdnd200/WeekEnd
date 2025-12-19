package com.devwork.weekend.post.postDTO;

import com.devwork.weekend.comment.commentDTO.CommentListDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PostListDTO {
    private long id;
    private long userId;
    private String name;
    private String profileImage;
    private String contents;
    private String imagePath;
    private List<CommentListDTO> comments;
    private int commentCount;
    private int likeCount;
    private boolean isLike;
    private boolean isFollow;
    private boolean isFollower;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
