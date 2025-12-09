package com.devwork.weekend.post.postDTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostDTO {
    private long id;
    private long userId;
    private String contents;
    private String imagePath;

}
