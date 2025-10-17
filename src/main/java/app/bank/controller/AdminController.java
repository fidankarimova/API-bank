package app.bank.controller;

import app.bank.entity.User;
import java.util.List;

public interface AdminController {

    List<User> getAllUsers(String token);

}
