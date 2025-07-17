package app.bank.controller;

import app.bank.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface AdminController {
    List<User> getAllUsers(String token);
}
