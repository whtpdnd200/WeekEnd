package com.devwork.weekend.post.postDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class WriteDTO {

    private String contents;
    private MultipartFile imagePath;
}
