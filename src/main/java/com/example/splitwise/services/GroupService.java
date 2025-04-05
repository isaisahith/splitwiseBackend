package com.example.splitwise.services;

import com.example.splitwise.models.*;
import com.example.splitwise.repositories.ExpenseRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserExpenseRepository;
import com.example.splitwise.repositories.UserRepository;
import com.example.splitwise.strategies.SettleUpStrategy;
import com.example.splitwise.strategies.SimpleSettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserExpenseRepository UserExpenseRepository;

    public Group createGroup(String name, String description, List<String> users) throws Exception {
        Group group = new Group();

        group.setGroupName(name);
        group.setDescription(description);

        List<User> userList = new ArrayList<>();

        for(String user : users) {
            Optional<User> userOptional = userRepository.findByEmail(user);
            if(userOptional.isEmpty()){
                throw new Exception(user+" not found");
            }
            userList.add(userOptional.get());
        }
        group.setUsers(userList);

        group.setAdmins(userList);
        groupRepository.save(group);
        return group;

    }

    public Group getGroup(Integer id) throws Exception {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if(groupOptional.isEmpty()){
            throw new Exception("Group not found");
        }

        return groupOptional.get();
    }

    public void addExpense(Integer groupId, String expenseDesc, List<String> paidBy, List<String> paidFor) throws Exception {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty()){
            throw new Exception("Group not found");
        }

        List<User> paidByUsers = new ArrayList<>();

        for(String user : paidBy) {
            Optional<User> userOptional = userRepository.findByEmail(user);
            if(userOptional.isEmpty()){
                throw new Exception(user+" not found");
            }
            paidByUsers.add(userOptional.get());
        }

        List<User> paidForUsers = new ArrayList<>();
        for(String user : paidFor) {
            Optional<User> userOptional = userRepository.findByEmail(user);
            if(userOptional.isEmpty()){
                throw new Exception(user+" not found");

            }
            paidForUsers.add(userOptional.get());
        }

        Expense expense = new Expense();
        expense.setGroup(groupOptional.get());
        expense.setExpenseName(expenseDesc);


    }

    public void addExpense(String name, String description, int groupId, int amount, List<Pair<String, Integer>> paidBy, List<Pair<String, Integer>> paidFor) throws Exception {
        Expense expense = new Expense();
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty()){
            throw new Exception("Group not found");
        }

        expense.setExpenseName(name);
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setGroup(groupOptional.get());

        int totalPaidBy = 0;
        int totalPaidFor = 0;

        List<UserExpense> paidByUsers = new ArrayList<>();
        List<UserExpense> paidForUsers = new ArrayList<>();

        for(Pair<String, Integer> pair : paidBy) {
            String email = pair.getFirst();
            Optional<User> userOptional = userRepository.findByEmail(email);
            if(userOptional.isEmpty()){
                throw new Exception(email+" not found");
            }
            int amt = pair.getSecond();
            totalPaidBy += amt;

            UserExpense userExpense = new UserExpense();
            userExpense.setUser(userOptional.get());
            userExpense.setAmount(amt);
            userExpense.setType(UserExpenseType.PAID_BY);
            UserExpenseRepository.save(userExpense);
            paidByUsers.add(userExpense);

        }


        for(Pair<String, Integer> pair : paidFor) {
            String email = pair.getFirst();
            Optional<User> userOptional = userRepository.findByEmail(email);
            if(userOptional.isEmpty()){
                throw new Exception(email+" not found");

            }
            int amt = pair.getSecond();
            totalPaidFor += amt;
            UserExpense userExpense = new UserExpense();
            userExpense.setUser(userOptional.get());
            userExpense.setAmount(amt);
            userExpense.setType(UserExpenseType.PAID_FOR);
            UserExpenseRepository.save(userExpense);
            paidForUsers.add(userExpense);

        }

        if(totalPaidFor!=totalPaidBy){
            throw new Exception("Total paid by users don't match");
        }

        expense.setPaidFor(paidForUsers);
        expense.setPaidBy(paidByUsers);
        expenseRepository.save(expense);


    }

    public List<Expense> getExpenses(Integer groupId) throws Exception {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty()){
            throw new Exception("Group not found");
        }

        List<Expense> expenses = expenseRepository.findByGroup(groupOptional.get());
        return expenses;

    }

    public List<Transaction> getSettleUpTransactions(Integer groupId) throws Exception {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty()){
            throw new Exception("Group not found");
        }

        List<Expense> expenses = expenseRepository.findByGroup(groupOptional.get());
        List<Transaction> transactions;

        SettleUpStrategy strategy = new SimpleSettleUpStrategy();
        transactions = strategy.settleUp(expenses);

        return transactions;
    }
}
