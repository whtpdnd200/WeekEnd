package com.devwork.weekend.comment.commentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentListDTO {

    private long id;
    private long postId;
    private long userId;
    private String name;
    private String profileImage;
    private String comment;
}
