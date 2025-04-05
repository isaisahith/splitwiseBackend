package com.example.splitwise.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private String toUser;
    private String fromUser;
    private int amount;

}
