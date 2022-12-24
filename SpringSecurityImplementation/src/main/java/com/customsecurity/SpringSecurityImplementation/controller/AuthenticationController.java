package com.customsecurity.SpringSecurityImplementation.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.Date;

import com.customsecurity.SpringSecurityImplementation.config.jwt.JwtTokenHelper;
import com.customsecurity.SpringSecurityImplementation.dto.*;
import com.customsecurity.SpringSecurityImplementation.model.PasswordResetToken;
import com.customsecurity.SpringSecurityImplementation.model.User;
import com.customsecurity.SpringSecurityImplementation.repository.PasswordResetTokenRepository;
import com.customsecurity.SpringSecurityImplementation.repository.UserRepository;
import com.customsecurity.SpringSecurityImplementation.service.CustomUserDetails;
import com.customsecurity.SpringSecurityImplementation.service.EmailService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticationController {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jWTTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUserName(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String jwtToken = jWTTokenHelper.generateToken(user.getUsername());


        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/forgot_password")
    private ResponseEntity<String> forgetPassword(HttpServletRequest request, @RequestBody ForgetPasswordRequest forgetPasswordRequest) {

        System.out.println(forgetPasswordRequest.getEmail());

        User user = userRepository.findByEmail(forgetPasswordRequest.getEmail());

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(RandomString.make(23));
        token.setUser(user);
        token.setExpiryDate(LocalDate.now());

        System.out.println("token :" + token.getToken());

        System.out.println(LocalDate.now() + " " + token.getExpiryDate());

        passwordResetTokenRepository.save(token);

        System.out.println("Send Email to :" + forgetPasswordRequest.getEmail());

        String resetPasswordLink = request.getRequestURL().toString().replace(request.getServletPath(), "") + "/api/v1/auth/reset_password?token=" + token.getToken();

        ForgetPasswordEmailResponse emailResponse = new ForgetPasswordEmailResponse();
        emailResponse.setRecipient(forgetPasswordRequest.getEmail());
        emailResponse.setSubject("Response for Change Password");
        emailResponse.setMsgBody("Click the link to visit the reset password page :" + resetPasswordLink);


        String response = emailService.sendSimpleMail(emailResponse);

        System.out.println(response);

        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    @PostMapping("/auth/reset_password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordRequest resetPasswordRequest) {

        String password = null;

        if (resetPasswordRequest.getPassword().equals(resetPasswordRequest.getConfirmPassword()))
            password = resetPasswordRequest.getPassword();

        System.out.println(password + " " + token);

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
// if password_token is not null then proceed

        User user = new User();
        BeanUtils.copyProperties(passwordResetToken.getUser(),user);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);


        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user) {
        CustomUserDetails userObj = (CustomUserDetails) userDetailsService.loadUserByUsername(user.getName());


        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName("Dipesh");
        userInfo.setLastName("Singh");
        userInfo.setRoles(userObj.getAuthorities().toArray());


        return ResponseEntity.ok(userInfo);

    }
}
