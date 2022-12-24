package com.customsecurity.SpringSecurityImplementation.repository;

import com.customsecurity.SpringSecurityImplementation.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    PasswordResetToken findByToken(String token);

}
