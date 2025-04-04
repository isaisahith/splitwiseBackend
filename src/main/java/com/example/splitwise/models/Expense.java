package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Expense extends BaseModel{
    private String expenseName;
    private String description;
    @ManyToOne
    private Group group;
    private int amount;
    @OneToMany
    private List<UserExpense> paidBy;
    @OneToMany
    private List<Expense> paidFor;
}
