package com.etse.ft.Service;

import com.etse.ft.Model.Authority;
import com.etse.ft.Model.RegisterUserDto;
import com.etse.ft.Model.Users;
import com.etse.ft.Repository.AuthorityRepository;
import com.etse.ft.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<Users> findUserById(Long id) {
        return usersRepository.findById(id);
    }

    @Override
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public Users saveUser(RegisterUserDto newUserDto) {
        if (usersRepository.findByUsername(newUserDto.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        Users user = new Users();
        user.setUsername(newUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        user.setName(newUserDto.getName());
        user.setAddress(newUserDto.getAddress());
        user.setCity(newUserDto.getCity());
        user.setStateCode(newUserDto.getStateCode());
        user.setZIP(newUserDto.getZIP());
        user.setActivated(true); // or based on your business logic

        Set<Authority> authorities = parseAuthorities(newUserDto.getRole());
        user.setAuthorities(authorities);

        return usersRepository.save(user);
    }

    private Set<Authority> parseAuthorities(String authoritiesStr) {
        Set<Authority> authoritySet = new HashSet<>();
        String[] roles = authoritiesStr.split(",");
        for (String role : roles) {
            String authorityName = role.contains("ROLE_") ? role : "ROLE_" + role.toUpperCase();
            Authority authority = authorityRepository.findById(authorityName)
                    .orElseThrow(() -> new IllegalArgumentException("Role does not exist: " + authorityName));
            authoritySet.add(authority);
        }
        return authoritySet;
    }

    @Override
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public Iterable<Users> findAllUsers() {
        return usersRepository.findAll();
    }
}
