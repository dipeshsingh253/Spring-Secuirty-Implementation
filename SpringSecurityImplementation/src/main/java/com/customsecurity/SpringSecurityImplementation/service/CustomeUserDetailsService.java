package com.customsecurity.SpringSecurityImplementation.service;

import com.customsecurity.SpringSecurityImplementation.model.User;
import com.customsecurity.SpringSecurityImplementation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByUsername(username);
        CustomeUserDetails customUser = new CustomeUserDetails(user);

        return customUser;
    }
}