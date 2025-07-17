package app.bank.service.impl;

import app.bank.exception.InvalidUserInput;
import app.bank.exception.NotEnoughMoney;
import app.bank.exception.UserAlreadyExists;
import app.bank.exception.UserNotFound;
import app.bank.repository.StatementRepository;
import app.bank.repository.UserRepository;
import app.bank.service.UserService;
import app.bank.entity.Statement;
import app.bank.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository,
                           StatementRepository statementRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.statementRepository = statementRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            System.out.println("User already exists: " + user.getUsername());
            throw new UserAlreadyExists("User already exists with username: " + user.getUsername());
        }
        System.out.println("Saving new user: " + user.getUsername());
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getBalance() == null) {
            user.setBalance(0);
        }
        return userRepository.save(user);
    }

    @Override
    public boolean loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        return user.isPresent() && encoder.matches(password, user.get().getPassword());
    }

    @Override
    public Integer getBalance(String username) throws RuntimeException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getBalance();
        } else {
            throw new UserNotFound("user not found");
        }
    }

    @Override
    public void deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        user.ifPresent(value -> userRepository.delete(value));
    }


    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String role = userRepository.findByUsername(user.getUsername()).get().getRole();
            return jwtService.generateToken(user.getUsername(), role);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    public List<Statement> statements(String username) {
        return statementRepository.findBySenderOrReceiver(username, username);
    }

}
