package app.bank.controller.impl;

import app.bank.controller.UserController;
import app.bank.service.UserService;
import app.bank.entity.User;
import app.bank.service.impl.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/bank/user")
public class UserControllerImpl implements UserController {

    private UserService userService;
    private JwtService jwtService;

    public UserControllerImpl(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;

    }

    @Override
    @PostMapping(path = "/register")
    public String  registerUser(@RequestBody User user) {
        User registerUser = userService.registerUser(user);
        return jwtService.generateToken(registerUser.getUsername());
    }

    @Override
    @PostMapping(path = "/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userService.verify(user);
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
