package com.example.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.List;

@Getter
@Setter
public class CreateExpenseDto {
    private String name;
    private String description;
    private int groupId;
    private int amount;
    private List<Pair<String, Integer>> paidBy;
    private List<Pair<String, Integer>> paidFor;
}
