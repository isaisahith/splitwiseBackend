package com.example.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGroupReqDto {
    private String name;
    private String description;
    private List<String> users;

}
