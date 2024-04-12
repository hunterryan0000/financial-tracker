package com.etse.ft.Service;

import com.etse.ft.Model.RegisterUserDto;
import com.etse.ft.Model.Users;

import java.util.Optional;

public interface UsersService {
    Optional<Users> findUserById(Long id);
    Users findByUsername(String username);
    Users saveUser(RegisterUserDto newUserDto);
    void deleteUser(Long id);
    Iterable<Users> findAllUsers();
    // Additional methods as required
}
