package com.devwork.weekend.passwordReset.repository;

import com.devwork.weekend.passwordReset.domain.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {


    public Optional<PasswordReset> findByCode(String code);
}
