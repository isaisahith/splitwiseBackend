package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "splitwise_group")
public class Group extends BaseModel {
    private String groupName;
    private String description;
    @ManyToMany
    private List<User> users;
    @ManyToMany
    private List<User> admins;
    @OneToMany
    private List<Expense> expenseList;
}
