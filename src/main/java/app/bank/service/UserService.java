package app.bank.service;

import app.bank.entity.Statement;
import app.bank.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    boolean loginUser(String username, String password);
    Integer getBalance(String username);
    void deleteUser(String username);
    String verify(User user);
    List<Statement> statements(String token);
}
