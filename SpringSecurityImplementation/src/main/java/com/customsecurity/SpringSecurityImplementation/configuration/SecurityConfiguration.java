package com.customsecurity.SpringSecurityImplementation.configuration;

import com.customsecurity.SpringSecurityImplementation.service.CustomeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            the order of ant matchers is very important to manage correctly , for example if you will do


               http
                .authorizeHttpRequests()
                .anyRequests().permitAll() or .antMatchers("/**").permitAll()  => then because of this line all the requests will be permitted without checking any other configurations even though we have written it .
                .antMatchers("/").permitAll()
                .antMatchers("/hello").authenticated()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/manager").hasRole("MANAGER")
                .antMatchers("/managerandadmin").hasAnyRole("MANAGER", "ADMIN")
                .and()
                .httpBasic();

                So be very careful while writing the ant matchers

         */

        http
                .authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/hello").authenticated()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/manager").hasRole("MANAGER")
                .antMatchers("/managerandadmin").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/allusers").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/test1").hasAuthority("ACCESS_TEST1")
                .antMatchers("/test2").hasAuthority("ACCESS_TEST2")
                .antMatchers("/test1or2").hasAnyAuthority("ACCESS_TEST1", "ACCESS_TEST2")
                .and()
                .httpBasic();

    }


    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
