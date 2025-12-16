package com.devwork.weekend.comment.commentDTO;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SliceCommentDTO {

    private List<CommentListDTO> content;
    private boolean hasNext;
    private long lastId;
}
