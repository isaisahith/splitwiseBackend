package com.example.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateExpenseResponseDto {
    private String message;
    private ResponseStatus responseStatus;
}
