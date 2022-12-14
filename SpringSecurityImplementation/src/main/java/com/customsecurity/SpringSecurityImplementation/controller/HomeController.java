package com.customsecurity.SpringSecurityImplementation.controller;

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
}
