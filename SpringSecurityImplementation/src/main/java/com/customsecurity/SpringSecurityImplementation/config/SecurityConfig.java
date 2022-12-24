package com.customsecurity.SpringSecurityImplementation.config;

import com.customsecurity.SpringSecurityImplementation.config.jwt.JwtAuthenticationFilter;
import com.customsecurity.SpringSecurityImplementation.config.jwt.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint).and()
                .authorizeRequests(
                        (request) -> request.antMatchers("/api/", "/api/v1/auth/login","/api/v1/auth/forgot_password", "/api/register","/api/v1/auth/reset_password/**").permitAll()
                                .antMatchers("/api/user").hasRole("USER")
                                .antMatchers("/api/user").hasAuthority("ACCESS1")
                                .antMatchers("/api/admin").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(userDetailsService, jwtTokenHelper),
                        UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable().cors().and().headers().frameOptions().disable();


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
