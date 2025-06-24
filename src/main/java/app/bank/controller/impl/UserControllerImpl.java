package app.bank.controller.impl;

import app.bank.controller.UserController;
import app.bank.service.UserService;
import app.bank.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/bank/user")
public class UserControllerImpl implements UserController {

    private UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping(path = "/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @Override
    @PostMapping(path = "/login")
    public boolean loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }

    @Override
    @GetMapping(path = "/balance/{username}")
    public Integer getBalance(@PathVariable String username) {
        return userService.getBalance(username);
    }

    @Override
    @GetMapping(path = "/list")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    @DeleteMapping(path = "/delete/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @Override
    @PutMapping(path = "/cashIn")
    public void cashIn(@RequestParam String username, @RequestParam int amount) {
        userService.cashIn(username, amount);
    }

    @Override
    @PutMapping(path = "/cashOut")
    public void cashOut(@RequestParam String username, @RequestParam int amount) {
        userService.cashOut(username, amount);
    }

    @Override
    @PutMapping(path = "/transfer")
    public void transfer(@RequestParam String sender, @RequestParam String receiver, @RequestParam int amount) {
        userService.transfer(sender, receiver, amount);
    }
}
