package com.etse.ft.Repository;

import com.etse.ft.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Spring Data JDBC will implement CRUD operations for you
}

