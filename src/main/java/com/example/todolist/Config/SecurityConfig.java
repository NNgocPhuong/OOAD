package com.example.todolist.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/tasks/**").hasAnyRole("MEMBER", "MANAGER")
            .requestMatchers("/admin/**").hasRole("MANAGER")
            .requestMatchers("/users/login", "/users/register").permitAll() // Cho phép truy cập không cần xác thực
            .requestMatchers("/users/**").hasRole("MANAGER") // Chỉ MANAGER có thể truy cập
            .anyRequest().authenticated()
            .and()
            .httpBasic(); // Sử dụng xác thực HTTP Basic
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
