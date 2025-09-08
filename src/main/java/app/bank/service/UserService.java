package app.bank.service;

import app.bank.entity.Statement;
import app.bank.entity.User;
import org.springframework.core.io.InputStreamResource;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    boolean loginUser(String username, String password);
    Integer getBalance(String username);
    void deleteUser(String username);
    String verify(User user);
    List<Statement> statements(String token);
    User updateUserImage(Integer id, String imageUrl);
    String getImage(String username);

}
