package com.booklending.customer.config;


import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity; 
import org.springframework.security.config.web.server.ServerHttpSecurity; 
import org.springframework.security.web.server.SecurityWebFilterChain; 
 
@Configuration 
@EnableWebFluxSecurity 
public class SecurityConfiguration { 
 
    @Bean 
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception { 
        return http 
                .csrf().disable() 
                .authorizeExchange() 
                .pathMatchers("/swagger-ui/**").permitAll() 
                .pathMatchers("/swagger-resources/*").permitAll() 
                .pathMatchers("/v3/api-docs/**").permitAll() 
                .pathMatchers("/login**").permitAll() 
                .pathMatchers("/customer/**").permitAll() 
                .and().build(); 
    } 
 
} 
