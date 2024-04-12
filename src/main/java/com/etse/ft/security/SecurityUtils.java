package com.etse.ft.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

// Utility class to interact with Spring Security's context for security-related operations.
public class SecurityUtils {

    // Logger instance for logging debug and informational messages.
    private static final Logger LOG = LoggerFactory.getLogger(SecurityUtils.class);

    // Private constructor to prevent instantiation of this utility class.
    private SecurityUtils() {
    }

    /**
     * Retrieves the username of the current authenticated user.
     *
     * @return An Optional<String> containing the username if present; otherwise, an empty Optional.
     */
    public static Optional<String> getCurrentUsername() {
        // Obtain the Authentication object from the SecurityContextHolder to access the current authentication details.
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // If no Authentication object is present, it means there is no current authentication information available.
        if (authentication == null) {
            // Log this scenario for debugging purposes.
            LOG.debug("no authentication in security context found");
            // Return an empty Optional indicating the absence of a username.
            return Optional.empty();
        }

        // Initialize a variable to hold the username, initially set to null.
        String username = null;

        // Check if the principal within the Authentication object is an instance of UserDetails.
        // UserDetails is a common interface in Spring Security used for user information.
        if (authentication.getPrincipal() instanceof UserDetails) {
            // Cast the principal to UserDetails to access user-specific methods.
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            // Retrieve the username from the UserDetails object.
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            // If the principal is a String, it is directly assigned as the username.
            // This scenario can occur in simpler authentication mechanisms.
            username = (String) authentication.getPrincipal();
        }

        // Log the found username for debugging purposes, using placeholder '{}' for the username value.
        LOG.debug("found username '{}' in security context", username);

        // Return the username wrapped in an Optional. If username is null, Optional.ofNullable will return an empty Optional.
        return Optional.ofNullable(username);
    }
}

