package com.example.splitwise.dtos;

import com.example.splitwise.models.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetSettleUpTransactionsResp {
    private List<Transaction> transactionList;
    private String message;
    private ResponseStatus responseStatus;
}
