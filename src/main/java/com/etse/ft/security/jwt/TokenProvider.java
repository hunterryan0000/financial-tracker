package com.etse.ft.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

// Indicates that this class is a Spring-managed component, making it eligible for Spring Dependency Injection and other Spring features.
@Component
public class TokenProvider implements InitializingBean {

    // Logger instance for logging messages, specific to this class.
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    // A constant key used for storing authorities information within the JWT.
    private static final String AUTHORITIES_KEY = "auth";

    // Encoded secret key for JWT generation and validation.
    private final String base64Secret;
    // Token validity period in milliseconds.
    private final long tokenValidityInMilliseconds;
    // Extended token validity period for the "remember me" functionality.
    private final long tokenValidityInMillisecondsForRememberMe;

    // Key instance used for signing JWTs.
    private Key key;

    // Constructor that initializes the TokenProvider with properties defined in application properties.
    public TokenProvider(
            @Value("${jwt.base64-secret}") String base64Secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
            @Value("${jwt.token-validity-in-seconds-for-remember-me}") long tokenValidityInSecondsForRememberMe) {
        this.base64Secret = base64Secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000; // Convert seconds to milliseconds.
        this.tokenValidityInMillisecondsForRememberMe = tokenValidityInSecondsForRememberMe * 1000; // Convert seconds to milliseconds for remember me functionality.
    }

    // Method from the InitializingBean interface, called after all the properties are set.
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret); // Decodes the base64 secret to get the byte array.
        this.key = Keys.hmacShaKeyFor(keyBytes); // Initializes the signing key with the decoded secret.
    }

    // Creates a JWT token based on the user's authentication and whether the "remember me" option is selected.
    public String createToken(Authentication authentication, boolean rememberMe) {
        // Concatenate authorities to a single string.
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        // Set the token expiration based on the remember me selection.
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        // Build and return the JWT token.
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    // Extracts authentication information from the provided token.
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        // Return an authentication token containing the user details and authorities.
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // Validates the provided JWT token.
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true; // Token is valid.
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature."); // Log signature issues.
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token."); // Log expiration issues.
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token."); // Log unsupported token issues.
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid."); // Log other issues with the token.
        }
        return false; // Token is invalid.
    }
}
/**
 * This class provides a comprehensive example of how to manage JWT (JSON Web Token) creation,
 * parsing, and validation within a Spring Boot application.
 * It demonstrates the use of Spring's @Component annotation for component scanning and dependency injection,
 * how to utilize Spring's InitializingBean to perform initialization after property setting,
 * and how to work with external configuration through @Value annotations. It also showcases practical
 * JWT operations with the java-jwt library, handling token creation based on user authentication,
 * extracting authentication from tokens, and validating tokens to ensure they are still valid and not tampered with.
 */