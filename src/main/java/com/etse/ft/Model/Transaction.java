package com.etse.ft.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "category")
    private String category;

    @Column(name = "the_date", nullable = false)
    private LocalDate date = LocalDate.now(); // Default to today's date

    public Transaction() {
        // Default constructor
    }

    // Constructor for all fields
    public Transaction(Long userId, String type, BigDecimal amount, String category) {
        this.userId = userId;
        setType(type); // Ensure type is valid upon creation
        this.amount = amount;
        this.category = category;
        this.date = LocalDate.now(); // Set date to now on creation
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Ensure the type is either "income" or "expense"
    public void setType(String type) {
        if (!type.equalsIgnoreCase("income") && !type.equalsIgnoreCase("expense")) {
            throw new IllegalArgumentException("Type must be either 'income' or 'expense'");
        }
        this.type = type;
    }
}

