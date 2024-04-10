package com.etse.ft.Repository;

import com.etse.ft.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Retention;

public interface UserRepository extends CrudRepository<User, Long> {
    // Spring Data JDBC will implement CRUD operations for you
    // You can define additional query methods here as needed

    User findByUsername(String username);
}
