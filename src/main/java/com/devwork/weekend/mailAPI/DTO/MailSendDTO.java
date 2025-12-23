package com.devwork.weekend.mailAPI.DTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MailSendDTO {

    private String from;                        // 발신자 이메일
    private String emailAddr;                   // 수신자 이메일
    private String subject;                     // 이메일 제목
    private String content;                     // 이메일 내용

}
