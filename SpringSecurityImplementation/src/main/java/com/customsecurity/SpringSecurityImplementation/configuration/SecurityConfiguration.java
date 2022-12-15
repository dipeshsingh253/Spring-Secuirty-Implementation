package com.customsecurity.SpringSecurityImplementation.configuration;

import com.customsecurity.SpringSecurityImplementation.jwt.JwtAuthenticationFilter;
import com.customsecurity.SpringSecurityImplementation.jwt.JwtAuthorizationFilter;
import com.customsecurity.SpringSecurityImplementation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

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
                // remove csrf and state in session because in jwt we do not need them
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // add jwt filters (1. authentication, 2. authorization)
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepository))
                .authorizeRequests()
                // configure access rules
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/manager").hasRole("MANAGER")
                .antMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated();

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
