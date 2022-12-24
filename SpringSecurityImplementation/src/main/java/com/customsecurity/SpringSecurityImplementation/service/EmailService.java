package com.customsecurity.SpringSecurityImplementation.service;

import com.customsecurity.SpringSecurityImplementation.dto.ForgetPasswordEmailResponse;

public interface EmailService {
    String sendSimpleMail(ForgetPasswordEmailResponse emailResponse);

}
