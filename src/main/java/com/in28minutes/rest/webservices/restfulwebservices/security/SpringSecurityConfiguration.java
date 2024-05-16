package com.in28minutes.rest.webservices.restfulwebservices.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Ensure all requests are authorized
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );

        //If not authenticated a web page should be shown
        http.httpBasic(Customizer.withDefaults());


        //CSRF -> POST, PUT
        http.csrf(csrf -> csrf.disable());


        return http.build();
    }


}
