package com.etse.ft.Repository;

import com.etse.ft.Model.Transaction;
import com.etse.ft.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    // Spring Data JDBC will implement CRUD operations for you
    // You can define additional query methods here as needed

    //nativeQuery: This attribute is a boolean that indicates whether the query is a native SQL query
    // or a JPQL query. Setting nativeQuery to true means the query specified in the value is a native SQL query, not JPQL.
    @Query(value = "SELECT * FROM USERS WHERE USERNAME = :username", nativeQuery = true)
    Users findByUsername(@Param("username") String username);
}
