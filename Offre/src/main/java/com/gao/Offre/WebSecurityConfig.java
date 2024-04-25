package com.gao.Offre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private JwtRequestFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .authorizeRequests()
                .requestMatchers("/offres/all").permitAll() // Permit access to /offres/all without authentication
                .requestMatchers("/offres/addoffre").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPERADMIN") // Require ROLE_ADMIN or ROLE_SUPERADMIN for /offres/addoffre
                .requestMatchers("/offres/deleteoffre/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPERADMIN") // Require ROLE_ADMIN or ROLE_SUPERADMIN for /offres/deleteoffre/**
                .requestMatchers("/offres/updateoffre").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPERADMIN") // Require ROLE_ADMIN or ROLE_SUPERADMIN for /offres/updateoffre
                .anyRequest().authenticated() // Require authentication for any other endpoint
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
