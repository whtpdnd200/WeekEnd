package com.devwork.weekend.mailAPI.service;

import com.devwork.weekend.mailAPI.DTO.MailSendDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSendServiceImpl implements MailSendService{

    private final JavaMailSender mailSender;

    public MailSendServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendHtmlMessage(MailSendDTO mailSendDTO) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(mailSendDTO.getFrom());
        helper.setTo(mailSendDTO.getEmailAddr());
        helper.setSubject(mailSendDTO.getSubject());
        helper.setText(mailSendDTO.getContent(), true); // true는 HTML 형식을 의미합니다

        mailSender.send(message);
    }
}
