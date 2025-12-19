package com.devwork.weekend.post.postDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostModifyDTO {

    private long id;
    private String contents;
    private MultipartFile imagePath;
}
