package app.bank.controller.impl;

import app.bank.controller.UserController;
import app.bank.entity.Statement;
import app.bank.service.UserService;
import app.bank.entity.User;
import app.bank.service.impl.JwtService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        //home/fidan/photos  + /profile.png  changeable
        //home/fidan/photos/profile.png
        //www.google.com/images/dasdadas/profile.pnf
        user.setId(null);
        user.setSentStatements(null);
        user.setReceivedStatements(null);
        User registerUser = userService.registerUser(user);
        return jwtService.generateToken(registerUser.getUsername(), registerUser.getRole());
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
    @GetMapping(path = "/balance")
    public Integer getBalance(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String username = jwtService.extractUserName(jwt);
        return userService.getBalance(username);
    }


    @Override
    @DeleteMapping(path = "/delete")
    public void deleteUser(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String username = jwtService.extractUserName(jwt);
        userService.deleteUser(username);
    }

    @Override
    @GetMapping(path = "/statements")
    public List<Statement> statements(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String username = jwtService.extractUserName(jwt);
        return userService.statements(username);
    }

    //upload with multipart use java nio
    //read from folder inputstreamresouce
    @PutMapping("/{id}/image")
    public User updateUserImage(@RequestHeader("Authorization") String token, @PathVariable Integer id, @RequestBody String imageUrl) {
        String jwt = token.substring(7);
        String username = jwtService.extractUserName(jwt);
        return userService.updateUserImage(id, imageUrl);
    }
    @GetMapping(value = "/image" )
    public String getImage(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String username = jwtService.extractUserName(jwt);
        return userService.getImage(username);
    }
}
