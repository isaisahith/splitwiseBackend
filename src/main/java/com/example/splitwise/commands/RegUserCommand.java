package com.example.splitwise.commands;

import com.example.splitwise.controllers.UserController;
import com.example.splitwise.dtos.RegUserRequestDto;
import com.example.splitwise.dtos.RegUserResponseDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUserCommand implements Command {
    @Override
    public boolean match(String command) {
        String[] commands = command.split(" ");

        if(commands.length != 4){
            return false;
        }

        if(!commands[0].equals("Reg")){
            return false;
        }

        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(commands[3]);

        if(!matcher.matches()){
            return false;
        }
        return true;
    }

    @Override
    public String execute(String command) {

        UserController userController = new UserController();

        RegUserRequestDto requestDto = new RegUserRequestDto();

        String[] commands = command.split(" ");

        requestDto.setUsername(commands[1]);
        requestDto.setPassword(commands[2]);
        requestDto.setEmail(commands[3]);

        RegUserResponseDto response = userController.registerUser(requestDto);

        String message = response.getUsername()+" "+ response.getStatus();
        return message;
    }
}
