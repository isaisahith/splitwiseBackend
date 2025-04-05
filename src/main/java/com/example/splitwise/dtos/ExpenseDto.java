package com.example.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExpenseDto {
    private String name;
    private String description;
    private int amount;
    private List<UserExpenseDto> userExpenses;
}
