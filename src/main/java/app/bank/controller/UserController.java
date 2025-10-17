package app.bank.controller;

import app.bank.entity.Statement;
import app.bank.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface UserController {

    String registerUser(User user);

    String loginUser(String username, String password);

    Integer getBalance(String token);

    void deleteUser(String username);

    List<Statement> statements(String token);

    String getImage(String username);

    ResponseEntity<String> uploadFile(MultipartFile file) throws IOException;

    ResponseEntity<byte[]> getFile(String token, String file) throws IOException;
}
