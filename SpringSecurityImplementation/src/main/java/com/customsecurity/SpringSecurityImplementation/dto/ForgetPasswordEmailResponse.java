package com.customsecurity.SpringSecurityImplementation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordEmailResponse {
        private String recipient;
        private String msgBody;
        private String subject;
        private String attachment;

}
