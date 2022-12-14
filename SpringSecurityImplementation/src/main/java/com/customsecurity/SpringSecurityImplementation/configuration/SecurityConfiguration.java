package com.customsecurity.SpringSecurityImplementation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN").authorities("ACCESS_TEST1")
                .and()
                .withUser("manager")
                .password(passwordEncoder().encode("manager123"))
                .roles("MANAGER").authorities("ACCESS_TEST1", "ACCESS_TEST2")
                .and()
                .withUser("dipesh")
                .password(passwordEncoder().encode("deep123"))
                .roles("USER");

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
                .antMatchers("/test1").hasAuthority("ACCESS_TEST1")
                .antMatchers("/test2").hasAuthority("ACCESS_TEST2")
                .antMatchers("/test1or2").hasAnyAuthority("ACCESS_TEST1", "ACCESS_TEST2")
                .and()
                .httpBasic();

    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
