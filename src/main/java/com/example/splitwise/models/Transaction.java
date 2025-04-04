package com.example.splitwise.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private User toUser;
    private User fromUser;
    private int amount;

}
