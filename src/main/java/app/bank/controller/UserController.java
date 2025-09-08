package app.bank.controller;

import app.bank.entity.Statement;
import app.bank.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserController {
    String registerUser(User user);
    String loginUser(String username, String password);
    Integer getBalance(String token);
    void deleteUser(String username);
    List<Statement> statements(String token);
    String getImage(String username);
}
