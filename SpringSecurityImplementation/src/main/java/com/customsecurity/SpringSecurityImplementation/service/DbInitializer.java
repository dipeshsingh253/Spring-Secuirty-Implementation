package com.customsecurity.SpringSecurityImplementation.service;

import com.customsecurity.SpringSecurityImplementation.model.User;
import com.customsecurity.SpringSecurityImplementation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();

        User user = new User(0, "dipesh", passwordEncoder.encode("password"), "", "");
        User admin = new User(0, "admin", passwordEncoder.encode("admin"), "ADMIN", "ACCESS_TEST1");
        User manager = new User(0, "manager", passwordEncoder.encode("manager"), "MANAGER", "ACCESS_TEST1,ACCESS_TEST2");

        List<User> users = Arrays.asList(user, admin, manager);

        userRepository.saveAll(users);

    }
}
