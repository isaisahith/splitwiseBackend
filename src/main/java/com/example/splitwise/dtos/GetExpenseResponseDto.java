package com.example.splitwise.dtos;

import com.example.splitwise.models.Expense;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetExpenseResponseDto {
    private int groupId;
    private List<ExpenseDto> expenses;
    private String message;
    private ResponseStatus responseStatus;
}
