package com.devwork.weekend.post.postDTO;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Builder
@Getter
public class SlicePostDTO {
    private List<PostListDTO> content;
    private boolean hasNext;
    private int number;
    private int size;
    private Pageable pageable;

}
