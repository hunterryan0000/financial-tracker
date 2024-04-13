package com.etse.ft.security;

import com.etse.ft.security.jwt.JWTConfigurer;
import com.etse.ft.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Enables Spring Web Security.
@EnableWebSecurity
// Allows method-level security annotations (@PreAuthorize, @Secured, etc.).
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // Autowired dependencies to integrate JWT with Spring Security.
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UserModelDetailsService userModelDetailsService;

    // Constructor for dependency injection.
    public WebSecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler,
          @Lazy UserModelDetailsService userModelDetailsService
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.userModelDetailsService = userModelDetailsService;
    }

    // Bean definition for password encoding, using BCrypt hashing algorithm.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures WebSecurity settings, such as specifying which requests should be ignored by Spring Security.
     * This is used to bypass Spring Security filters for specific paths.
     *
     * @param web the WebSecurity to configure
     */
    @Override
    public void configure(WebSecurity web) {
        // Ignore all OPTIONS HTTP requests to avoid CORS issues.
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    /**
     * Configures HttpSecurity to specify request authorizations, configure session management,
     * and set up exception handling.
     *
     * @param httpSecurity the HttpSecurity to modify
     * @throws Exception thrown by HttpSecurity configurations
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Permit all requests to the H2 console and authentication paths without authentication
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()  // Allow everyone to access H2 console
                .antMatchers("/login", "/register").permitAll() // Allow everyone to access authentication paths
                .anyRequest().authenticated() // All other requests need to be authenticated

                // Disable CSRF (Cross-Site Request Forgery) as JWT is used
                .and()
                .csrf().disable()

                // Configure exception handling for unauthenticated requests and access denied situations
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // Configure Spring Security to not create or use any session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // Apply additional JWT security configurations
                .and()
                .apply(securityConfigurerAdapter())

                // Allow use of frame to same origin URLs (necessary for H2 console)
                .and()
                .headers().frameOptions().sameOrigin();
    }

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                // Disable CSRF (Cross-Site Request Forgery) protection as JWT is used, which is inherently protected against CSRF.
//                .csrf().disable()
//
//
//                // Configure exception handling for unauthenticated requests and access denied situations.
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//
//                // Configure Spring Security to not create or use any session.
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                // Apply additional JWT security configurations.
//                .and()
//                .apply(securityConfigurerAdapter());
//
//
//    }

    // Helper method to initialize and return the JWTConfigurer, which integrates the TokenProvider with Spring Security.
    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}


