package com.example.korera.config;


import com.example.korera.filters.JwtAuthenticationFilter;
import com.example.korera.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,  MyUserDetailsService myUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                })
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/user/login","/user/register").permitAll()
                        .anyRequest().fullyAuthenticated()
                ).addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(generatePassWordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider);
        return new ProviderManager(providers);
    }

    @Bean
    public PasswordEncoder generatePassWordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
