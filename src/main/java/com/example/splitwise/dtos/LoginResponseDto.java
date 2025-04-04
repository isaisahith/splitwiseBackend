package com.example.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String username;
    private String message;
    private ResponseStatus responseStatus;
}
