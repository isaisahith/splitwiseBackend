package com.example.splitwise.commands;

public interface Command {
    boolean match(String command);
    String execute(String command);
}
