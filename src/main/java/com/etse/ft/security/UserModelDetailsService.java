package com.etse.ft.security;

import com.etse.ft.Service.UsersService;
import com.etse.ft.Model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
// Marks this class as a Spring-managed component named "userDetailsService".
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

    // Logger instance for logging debug messages.
    private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

    // Data access object to fetch user data from the database.
    private final UsersService userService;

    // Constructor for dependency injection, injecting a UserDao to access the user repository.
    public UserModelDetailsService(@Lazy UsersService userService) {
        this.userService = userService;
    }

    /**
     * Loads the user details by username.
     *
     * @param login the username of the user to load.
     * @return UserDetails object containing the user's information and authorities.
     */
    @Override
    public UserDetails loadUserByUsername(final String login) {
        // Log the authentication attempt for debugging purposes.
        log.debug("Authenticating user '{}'", login);

        // Convert the login to lowercase to ensure case-insensitive login.
        String lowercaseLogin = login.toLowerCase();

        // Fetch the user from the database and create a Spring Security User object.
        return createSpringSecurityUser(lowercaseLogin, userService.findByUsername(lowercaseLogin));
    }

    /**
     * Creates a Spring Security User object from the application's User entity.
     *
     * @param lowercaseLogin the username in lowercase.
     * @param user the user entity retrieved from the database.
     * @return a Spring Security User object with authorities.
     */
    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, Users user) {
        // Check if the user is activated. Throw an exception if not.
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }

        // Convert the user's authorities from the application domain to Spring Security GrantedAuthority objects.
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        // Create and return the Spring Security User object with username, password, and authorities.
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}

