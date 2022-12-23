package com.customsecurity.SpringSecurityImplementation.controller;


import com.customsecurity.SpringSecurityImplementation.model.User;
import com.customsecurity.SpringSecurityImplementation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<String> accessAll() {

        return new ResponseEntity<>("Hello All", HttpStatus.OK);

    }


    @GetMapping("/user")
    public ResponseEntity<String> accessUser() {

        return new ResponseEntity<>("Hello User", HttpStatus.OK);

    }

    @GetMapping("/admin")
    public ResponseEntity<String> accessAdmin() {

        return new ResponseEntity<>("Hello Admin", HttpStatus.OK);

    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> accessAuthenticated() {

        return new ResponseEntity<>("Hello Authnticated", HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);


        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

}
