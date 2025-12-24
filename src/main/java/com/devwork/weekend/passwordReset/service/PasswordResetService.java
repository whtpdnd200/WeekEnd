package com.devwork.weekend.passwordReset.service;

import com.devwork.weekend.passwordReset.domain.PasswordReset;
import com.devwork.weekend.passwordReset.repository.PasswordResetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;

    public String getUuid() {

        return UUID.randomUUID().toString();
    }

    public boolean createPasswordReset(String email) {

        PasswordReset passwordReset = PasswordReset.builder()
                .email(email)
                .code(getUuid())
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
