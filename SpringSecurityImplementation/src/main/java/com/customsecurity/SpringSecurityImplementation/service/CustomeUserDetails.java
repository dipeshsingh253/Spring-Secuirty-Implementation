package com.customsecurity.SpringSecurityImplementation.service;

import com.customsecurity.SpringSecurityImplementation.model.User;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomeUserDetails implements UserDetails {


    private final User user;

    public CustomeUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<>();

        this.user.getAuthoritiesList().forEach(el -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(el);
            authorityList.add(authority);
        });


        this.user.getRolesList().forEach(el -> {
            GrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + el);
            authorityList.add(role);
        });


        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
