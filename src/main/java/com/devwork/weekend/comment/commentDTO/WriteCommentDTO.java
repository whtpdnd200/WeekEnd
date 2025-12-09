package com.devwork.weekend.comment.commentDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteCommentDTO {

    private long postId;
    private String comment;
}
