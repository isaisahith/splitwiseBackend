package com.example.splitwise.controllers;

import com.example.splitwise.dtos.CreateGroupReqDto;
import com.example.splitwise.dtos.CreateGroupRespDto;
import com.example.splitwise.dtos.ResponseStatus;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import com.example.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
