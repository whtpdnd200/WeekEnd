package com.devwork.weekend.post.postDTO;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListDTO {

    private long id;
    private long userId;
    private String name;
    private String profileImage;
    private String contents;
    private String imagePath;

}
