package com.etse.ft.Controller;

import com.etse.ft.Model.Transaction;
import com.etse.ft.Service.TransactionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return service.save(transaction);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return service.getTransactionById(id).orElse(null);
    }

    @GetMapping
    public Iterable<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        service.deleteById(id);
    }
}

