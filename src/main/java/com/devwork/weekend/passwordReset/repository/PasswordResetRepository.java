package com.devwork.weekend.passwordReset.repository;

import com.devwork.weekend.passwordReset.domain.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {


}
