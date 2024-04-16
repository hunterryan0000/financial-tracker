package com.etse.ft.Service;

import com.etse.ft.Model.Transaction;
import com.etse.ft.Model.Users;
import com.etse.ft.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository repository;
    private final UsersService usersService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UsersService usersService) {
        this.repository = transactionRepository;
        this.usersService = usersService;
    }

    public Transaction saveTransaction(Principal principal, String type, BigDecimal amount, String category) {
        Users user = usersService.findByUsername(principal.getName());
        Transaction transaction = new Transaction();
        transaction.setUserId(user.getId()); // Assumed to be Integer now
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDate(LocalDate.now()); // Optionally set the date
        return repository.save(transaction);
    }


    public Optional<Transaction> getTransactionById(Principal principal, Long transactionId) {
        Users user = usersService.findByUsername(principal.getName());
        return repository.findByIdAndUserId(transactionId, user.getId());
    }

    public List<Transaction> getTransactionsByUserId(Principal principal) {
        Users user = usersService.findByUsername(principal.getName());
        return repository.findByUserId(user.getId());
    }

    public List<Transaction> getTransactionsByTypeCategoryDate(Principal principal, String type, String category, LocalDate date) {
        Users user = usersService.findByUsername(principal.getName());
        if (type == null && category == null && date == null) {
            return repository.findByUserId(user.getId());  // Assuming this returns all transactions for the user
        } else {
            return repository.findByUserIdAndTypeAndCategoryAndDate(user.getId(), type, category, date);
        }
    }


    @Transactional
    public void deleteTransactionById(Principal principal, Long transactionId) {
        Users user = usersService.findByUsername(principal.getName());
        repository.deleteByIdAndUserId(user.getId(), transactionId);
    }


}



