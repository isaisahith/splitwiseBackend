package com.example.splitwise.dtos;

import com.example.splitwise.models.UserExpenseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExpenseDto {
    private String email;
    private String name;
    private int amount;
    private UserExpenseType type;
}
