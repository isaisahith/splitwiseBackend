package com.example.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGroupRespDto {
    private String groupName;
    private String groupDescription;
    private List<String> users;
    private String message;
    private ResponseStatus responseStatus;
}
