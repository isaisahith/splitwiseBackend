package com.example.splitwise.repositories;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByGroup(Group group);
}
