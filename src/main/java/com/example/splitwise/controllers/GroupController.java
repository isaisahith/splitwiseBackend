package com.example.splitwise.controllers;

import com.example.splitwise.dtos.*;
import com.example.splitwise.dtos.ResponseStatus;
import com.example.splitwise.models.*;
import com.example.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    public CreateGroupRespDto createGroup(@RequestBody  CreateGroupReqDto createGroupReqDto) {
        CreateGroupRespDto createGroupRespDto = new CreateGroupRespDto();
        try{
            Group group = groupService.createGroup(createGroupReqDto.getName(), createGroupReqDto.getDescription(), createGroupReqDto.getUsers());
            createGroupRespDto.setGroupName(group.getGroupName());
            createGroupRespDto.setGroupDescription(group.getDescription());
            List<String> users = new ArrayList<>();
            for(User user : group.getUsers()){
                users.add(user.getName());
            }
            createGroupRespDto.setUsers(users);
            createGroupRespDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {
            createGroupRespDto.setMessage(e.getMessage());
            createGroupRespDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return createGroupRespDto;
    }

    @PostMapping("/addExpense")
    public CreateExpenseResponseDto addExpense(@RequestBody CreateExpenseDto req){
        CreateExpenseResponseDto response = new CreateExpenseResponseDto();
        try{
            response.setResponseStatus(ResponseStatus.SUCCESS);
            groupService.addExpense(req.getName(),
                    req.getDescription(),
                    req.getGroupId(),
                    req.getAmount(),
                    req.getPaidBy(),
                    req.getPaidFor());

        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    @GetMapping("/getExpenses")
    public GetExpenseResponseDto getExpenses(@RequestBody GetExpenseReqDto req){
        GetExpenseResponseDto response = new GetExpenseResponseDto();
        try{
            List<Expense> expenses = groupService.getExpenses(req.getGroupId());
            List<ExpenseDto> expenseDtos = new ArrayList<>();
            for(Expense expense: expenses){
                List<UserExpenseDto> userExpenseDtos = new ArrayList<>();
                for(UserExpense ue: expense.getPaidBy()){
                    UserExpenseDto userExpenseDto = new UserExpenseDto();
                    userExpenseDto.setName(ue.getUser().getName());
                    userExpenseDto.setEmail(ue.getUser().getEmail());
                    userExpenseDto.setAmount(ue.getAmount());
                    userExpenseDto.setType(ue.getType());
                    userExpenseDtos.add(userExpenseDto);
                }
                ExpenseDto expenseDto = new ExpenseDto();
                expenseDto.setName(expense.getExpenseName());
                expenseDto.setDescription(expense.getDescription());
                expenseDto.setAmount(expense.getAmount());
                for(UserExpense ue: expense.getPaidFor()){
                    UserExpenseDto userExpenseDto = new UserExpenseDto();
                    userExpenseDto.setName(ue.getUser().getName());
                    userExpenseDto.setEmail(ue.getUser().getEmail());
                    userExpenseDto.setAmount(ue.getAmount());
                    userExpenseDto.setType(ue.getType());
                    userExpenseDtos.add(userExpenseDto);

                }
                expenseDto.setUserExpenses(userExpenseDtos);
                expenseDtos.add(expenseDto);
            }
            response.setExpenses(expenseDtos);
            response.setGroupId(req.getGroupId());
            response.setResponseStatus(ResponseStatus.SUCCESS);


        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;

    }

    @GetMapping("/getSettleUp")
    public GetSettleUpTransactionsResp getSettleUpTransactions(@RequestBody GetSettleUpTransactionsReqDto req){
        GetSettleUpTransactionsResp response = new GetSettleUpTransactionsResp();
        try{
            List<Transaction> transactionList = groupService.getSettleUpTransactions(req.getGroupId());
            response.setTransactionList(transactionList);
            response.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setResponseStatus(ResponseStatus.FAILURE);
        }

        return response;
    }
}
