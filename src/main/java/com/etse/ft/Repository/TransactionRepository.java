package com.etse.ft.Repository;

import com.etse.ft.Model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    // Spring Data JDBC will implement CRUD operations for you
}

