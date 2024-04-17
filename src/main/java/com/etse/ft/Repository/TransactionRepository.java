package com.etse.ft.Repository;

import com.etse.ft.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    @Query("SELECT t FROM Transaction t WHERE t.userId = :userId AND (:type IS NULL OR t.type = :type) AND (:category IS NULL OR t.category = :category) AND (:date IS NULL OR t.date = :date)")
    List<Transaction> findByUserIdAndTypeAndCategoryAndDate(@Param("userId") Long userId, @Param("type") String type, @Param("category") String category, @Param("date") LocalDate date);

    Optional<Transaction> findByIdAndUserId(Long id, Long userId);

    // Custom query to delete a transaction by ID and user ID
    @Modifying //meaning any operation that involves updates, deletes, or even native INSERT commands. By default, @Query assumes the operation is a SELECT operation, so this annotation modifies that behavior.
    @Transactional //ensures that the operation is part of a transaction and thus can be rolled back if part of the operation fails, maintaining data integrity.
    @Query("DELETE FROM Transaction t WHERE t.id = :transactionId AND t.userId = :userId")
    void deleteByIdAndUserId(@Param("userId") Long userId, @Param("transactionId") Long transactionId);
}




