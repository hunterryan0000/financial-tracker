package com.etse.ft.Controller;

import com.etse.ft.Model.LoginDto;
import com.etse.ft.Model.LoginResponseDto;
import com.etse.ft.Model.RegisterUserDto;
import com.etse.ft.Model.Users;
import com.etse.ft.Service.UsersService;
import com.etse.ft.security.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * AuthenticationController is a class used for handling requests to authenticate Users.
 *
 * It depends on an instance of a UserDao for retrieving and storing user data. This is provided
 * through dependency injection.
 */
// Marks this class as a controller where every method returns a domain object instead of a view.
// It's ideal for building RESTful web services.
@RestController
// Allows cross-origin requests, enabling this controller to accept requests from different domains,
// which is common in modern web applications where the backend and frontend are hosted separately.
@CrossOrigin
public class AuthController {

    // Dependencies are injected via the constructor. These are used for generating tokens and authenticating users.
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UsersService userService;

    // Constructor for dependency injection, ensuring that all required components are available for authentication processes.
    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UsersService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
    }

    // Endpoint for user login. It expects a POST request with LoginDto object in the request body.
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponseDto login(@Valid @RequestBody LoginDto loginDto) {
        // Creates an authentication token with the provided username and password.
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // Authenticates the user with the configured authentication manager.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Sets the authentication in the SecurityContext, effectively logging in the user for this session.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generates a JWT for the authenticated user.
        String jwt = tokenProvider.createToken(authentication, false);

        // Retrieves the user details from the database to include in the login response.
        Users user = userService.findByUsername(loginDto.getUsername());
        // Returns the JWT and user details as the login response.
        return new LoginResponseDto(jwt, user);
    }

    // Endpoint for user registration. It expects a POST request with RegisterUserDto object in the request body.
    @ResponseStatus(HttpStatus.CREATED) // Indicates that a new entity was created.
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Users register(@Valid @RequestBody RegisterUserDto newUser) {
        Users existingUser = userService.findByUsername(newUser.getUsername());
        if (existingUser != null) {
            // If a user is found, throw an exception indicating the username is already taken.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User name already exists.");
        }
        // No existing user found, proceed with creating a new user
        Users user = userService.saveUser(newUser);
        return user;
    }

}
