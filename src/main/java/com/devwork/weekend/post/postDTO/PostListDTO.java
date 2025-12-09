package com.devwork.weekend.post.postDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class PostListDTO {
    private long id;
    private long userId;
    private String name;
    private String contents;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
