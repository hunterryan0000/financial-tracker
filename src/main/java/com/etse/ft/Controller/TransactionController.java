package com.etse.ft.Controller;

import com.etse.ft.Model.Transaction;
import com.etse.ft.Model.TransactionDTO;
import com.etse.ft.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@PreAuthorize("isAuthenticated()")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
        public ResponseEntity<Transaction> createTransaction(
            Principal principal,
            @RequestBody TransactionDTO transactionDTO) {
        Transaction createdTransaction = transactionService.saveTransaction(
                principal,
                transactionDTO.getType(),
                transactionDTO.getAmount(),
                transactionDTO.getCategory()
        );
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(
            Principal principal,
            @PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(principal, id);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Transaction>> getMyTransactions(Principal principal) {
        List<Transaction> transactions = transactionService.getTransactionsByUserId(principal);
        return transactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactions);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Transaction>> getTransactionsByTypeCategoryDate(
            Principal principal,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Transaction> transactions = transactionService.getTransactionsByTypeCategoryDate(principal, type, category, date);
        return transactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            Principal principal,
            @PathVariable Long id) {
        transactionService.deleteTransactionById(principal, id);
        return ResponseEntity.ok().build();  // Consider checking if deletion was successful and respond accordingly
    }
}

