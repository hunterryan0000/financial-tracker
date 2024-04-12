package com.etse.ft.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;

    public JWTConfigurer(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) { //This overridden method configures the Spring Security filter chain.
        JWTFilter customFilter = new JWTFilter(tokenProvider);

// Adding the Filter to the Chain: Then, http.addFilterBefore() method is called to add this custom filter into the Spring Security filter chain.
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        // This ensures that your JWTFilter will be executed before the built-in UsernamePasswordAuthenticationFilter
    }
}
