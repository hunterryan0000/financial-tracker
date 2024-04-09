package com.etse.ft.Model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record Transaction(Long id, String type, BigDecimal amount, String category, LocalDate date) {}
