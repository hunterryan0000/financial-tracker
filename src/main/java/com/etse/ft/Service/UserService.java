package com.etse.ft.Service;

import com.etse.ft.Model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Long id);
    User findByUsername(String username);
    User saveUser(User user);
    void deleteUser(Long id);
    Iterable<User> findAllUsers();
    // Additional methods as required
}
