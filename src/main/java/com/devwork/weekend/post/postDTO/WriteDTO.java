package com.devwork.weekend.post.postDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class WriteDTO {

    private long userId;
    private String contents;
    private String imagePath;
}
