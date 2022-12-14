package com.customsecurity.SpringSecurityImplementation.controller;

import com.customsecurity.SpringSecurityImplementation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @GetMapping("/")
    public String helloGeneral() {
        return "Hello World...To Everyone!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World...!";
    }


    @GetMapping("/user")
    public String user() {
        return "Hello World User...!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello World Admin...!";
    }

    @GetMapping("/manager")
    public String manager() {
        return "Hello World Manager...!";
    }


    @GetMapping("/managerandadmin")
    public String managerAndAdmin() {
        return "Hello World Manager or Admin...!";
    }

    @GetMapping("/test1")
    public String authTestOne() {
        return "It means you have authority 1...!";
    }

    @GetMapping("/test2")
    public String authTestTwo() {
        return "It means you have authority 2...!";
    }

    @GetMapping("/test1or2")
    public String authTestOneOrTwo() {
        return "It means you have authority 1 or 2...!";
    }



}
