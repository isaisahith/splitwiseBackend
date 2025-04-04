package com.example.splitwise.controllers;

import com.example.splitwise.dtos.*;
import com.example.splitwise.models.User;
import com.example.splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public RegUserResponseDto registerUser(@RequestBody  RegUserRequestDto requestDto){
        RegUserResponseDto responseDto = new RegUserResponseDto();
            System.out.println(requestDto.getUsername());
            System.out.println(requestDto.getEmail());
            System.out.println(requestDto.getPassword());

        try{
            User user = userService.registerUser(
                    requestDto.getUsername(),
                    requestDto.getEmail(),
                    requestDto.getPassword()
                    );
            responseDto.setUsername(user.getName());
            responseDto.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    @PostMapping("/login")
    public LoginResponseDto loginUser(@RequestBody LoginRequestDto requestDto){
        LoginResponseDto responseDto = new LoginResponseDto();

        try{
            User user = userService.loginUser(requestDto.getEmail(), requestDto.getPassword());
            responseDto.setUsername(user.getName());
            responseDto.setMessage("Login Successful");
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setMessage(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
