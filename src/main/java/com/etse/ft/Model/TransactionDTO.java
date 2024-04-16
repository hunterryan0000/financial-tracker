package com.etse.ft.Model;

import java.math.BigDecimal;

public class TransactionDTO {
    private String type;
    private BigDecimal amount;
    private String category;

    // Constructors, getters, and setters

    public TransactionDTO() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}

