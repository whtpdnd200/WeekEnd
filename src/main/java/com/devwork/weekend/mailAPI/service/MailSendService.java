package com.devwork.weekend.mailAPI.service;

import com.devwork.weekend.mailAPI.DTO.MailSendDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface MailSendService {

    public boolean sendHtmlMessage(MailSendDTO mailSendDTO) throws MessagingException;
}
