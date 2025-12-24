package com.devwork.weekend.passwordReset.service;

import com.devwork.weekend.mailAPI.DTO.MailSendDTO;
import com.devwork.weekend.mailAPI.service.MailSendServiceImpl;
import com.devwork.weekend.passwordReset.domain.PasswordReset;
import com.devwork.weekend.passwordReset.repository.PasswordResetRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    private final MailSendServiceImpl mailSendService;

    public String getUuid() {

        return UUID.randomUUID().toString();
    }

    @Transactional
    public boolean sendMailAndInsert(String email) {
        MailSendDTO mailSendDTO = createMailForm(email);

        if(sendEmail(mailSendDTO) && createPasswordReset(mailSendDTO)) {
            return true;
        }

        return false;
    }

    public MailSendDTO createMailForm(String email) {
        String uuid = getUuid();
        String from = "whtpdnd200@gmail.com";
        String subject = "비밀번호 변경 링크 입니다.";
        String content = "<hr>" +
                "<div>" +
                "   <a href=/user/password-change?code=" + uuid + ">http:://localhost/user/password-change?code=" + uuid + "</a>" +
                "</div>" +
                "<div>해당 링크는 10분간만 유효 합니다</div>";
        MailSendDTO mailSendDTO = MailSendDTO.builder()
                .from(from)
                .emailAddr(email)
                .subject(subject)
                .content(content)
                .uuid(uuid)
                .build();

        return mailSendDTO;
    }

    public boolean sendEmail(MailSendDTO mailSendDTO) {

        return mailSendService.sendHtmlMessage(mailSendDTO);
    }

    public boolean createPasswordReset(MailSendDTO mailSendDTO) {

        PasswordReset passwordReset = PasswordReset.builder()
                .email(mailSendDTO.getEmailAddr())
                .code(mailSendDTO.getUuid())
                .used(false)
                .expiredAt(LocalDateTime.now().plusMinutes(10))
                .build();

        try {
            passwordResetRepository.save(passwordReset);
        } catch(DataAccessException e) {
            return false;
        }
        return true;
    }
}
