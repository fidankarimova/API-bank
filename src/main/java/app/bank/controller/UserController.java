package app.bank.controller;

import app.bank.user.User;

import java.util.List;

public interface UserController {
    User registerUser(User user);
    boolean loginUser(String username, String password);
    Integer getBalance(String username);
    List<User> getAllUsers();
    void deleteUser(String username);
    void cashIn(String username, int amount);
    void cashOut(String username, int amount);
    void transfer(String sender, String receiver, int amount);
}
