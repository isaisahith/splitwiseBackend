package com.example.splitwise.strategies;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Transaction;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settleUp(List<Expense> expenseList);
}
