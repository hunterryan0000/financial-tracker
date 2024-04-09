package com.etse.ft.Repository;

import com.etse.ft.Model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    // Spring Data JDBC will implement CRUD operations for you
}

