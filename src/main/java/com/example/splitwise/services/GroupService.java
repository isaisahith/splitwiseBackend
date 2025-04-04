package com.example.splitwise.services;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.ExpenseRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
