package app.bank.controller;

import app.bank.entity.User;

import java.util.List;

public interface UserController {
    String registerUser(User user);
    String loginUser(String username, String password);
    Integer getBalance(String username);
    List<User> getAllUsers();
    void deleteUser(String username);
    void cashIn(String username, int amount);
    void cashOut(String username, int amount);
    void transfer(String sender, String receiver, int amount);
}
