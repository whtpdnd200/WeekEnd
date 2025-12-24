package com.devwork.weekend.passwordReset.service;

import com.devwork.weekend.mailAPI.DTO.MailSendDTO;
import com.devwork.weekend.mailAPI.service.MailSendServiceImpl;
import com.devwork.weekend.passwordReset.PasswordResetDTO.PasswordResetBasicDTO;
import com.devwork.weekend.passwordReset.domain.PasswordReset;
import com.devwork.weekend.passwordReset.repository.PasswordResetRepository;
import com.devwork.weekend.user.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    private final MailSendServiceImpl mailSendService;
    public final UserService userService;

    public String getUuid() {

        return UUID.randomUUID().toString();
    }

    public boolean getUserInfo(String memberId, String email) {

        return userService.existsByMemberIdAndEmail(memberId, email);
    }

    @Transactional
    public boolean sendMailAndInsert(String memberId, String email) {

        if(getUserInfo(memberId, email)) {
            MailSendDTO mailSendDTO = createMailForm(email);

            if(sendEmail(mailSendDTO) && createPasswordReset(mailSendDTO)) {
                return true;
            }
        }
        return false;
    }

    public MailSendDTO createMailForm(String email) {
        String uuid = getUuid();
        String from = "whtpdnd200@gmail.com";
        String subject = "비밀번호 변경 링크 입니다.";
        String content = "<hr>" +
                "<div>" +
                "   <a href=http://localhost:8080/user/password-change?code=" + uuid + ">http://localhost:8080/user/password-change?code=" + uuid + "</a>" +
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

    public PasswordResetBasicDTO getCodeInfo(String code) {

        Optional<PasswordReset> optionalPasswordReset = passwordResetRepository.findByCode(code);

        if(optionalPasswordReset.isPresent()) {
            PasswordReset passwordReset = optionalPasswordReset.get();
            PasswordResetBasicDTO passwordResetBasicDTO = PasswordResetBasicDTO.builder()
                    .id(passwordReset.getId())
                    .email(passwordReset.getEmail())
                    .code(passwordReset.getCode())
                    .used(passwordReset.isUsed())
                    .expiredAt(passwordReset.getExpiredAt())
                    .build();

            return passwordResetBasicDTO;
        }
        return null;
    }

    public boolean isValidation(PasswordResetBasicDTO passwordResetBasicDTO) {


        if(passwordResetBasicDTO == null || passwordResetBasicDTO.isUsed() || LocalDateTime.now().isAfter(passwordResetBasicDTO.getExpiredAt())) {
            return false;
        }

        return true;
    }

    public boolean updatePasswordReset(String code, String email, String password) {

        Optional<PasswordReset> optionalPasswordReset = passwordResetRepository.findByCode(code);

        if(optionalPasswordReset.isPresent()) {
            PasswordReset passwordReset = optionalPasswordReset.get();

            passwordReset = PasswordReset.builder()
                    .code(passwordReset.getCode())
                    .email(passwordReset.getEmail())
                    .expiredAt(passwordReset.getExpiredAt())
                    .used(true)
                    .build();

            try {
                passwordResetRepository.save(passwordReset);
                userService.updatePassword(email, password);
            } catch(DataAccessException e) {
                return false;
            }
        }

        return true;
    }
}
