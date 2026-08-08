package com.vikash.auth_service.config;

import com.vikash.auth_service.security.CustomAccessDeniedHandler;
import com.vikash.auth_service.security.JwtAuthenticationEntryPoint;
import com.vikash.auth_service.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration // That denote the security configuration file
@EnableWebSecurity // Disable the default web security chain
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    //We use UserName as Email id

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean // Use for create bean of any method
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .cors(cors -> {})
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Tells don't create or use JSESSIONID
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/oauth2/**",
                                "/login/**",
                                "/actuator/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class )
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //Convert plain text into hash but not vise versa
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
