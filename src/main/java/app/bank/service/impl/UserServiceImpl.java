package app.bank.service.impl;

import app.bank.exception.UserAlreadyExists;
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

        if(user.isPresent() && encoder.matches(password, user.get().getPassword())) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Integer getBalance(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getBalance();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public void deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            userRepository.delete(user.get());
        }
    }

    @Override
    public void cashIn(String username, int amount) {
        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isPresent()) {
            User user = optional.get();
            int balance = user.getBalance();
            balance += amount;
            user.setBalance(balance);
            userRepository.save(user);
        }

        Statement statement = new Statement();
        statement.setType("Cash in");
        statement.setAmount(amount);
        statement.setDate(LocalDateTime.now());
        statement.setReceiver(username);
        statement.setSender(null);

        statementRepository.save(statement);
    }

    @Override
    public void cashOut(String username, int amount) {
        Optional<User> optional = userRepository.findByUsername(username);
        if(optional.isPresent()) {
            User user = optional.get();
            int balance = user.getBalance();
            balance -= amount;
            user.setBalance(balance);
            userRepository.save(user);
        }

        Statement statement = new Statement();
        statement.setType("Cash out");
        statement.setAmount(amount);
        statement.setDate(LocalDateTime.now());
        statement.setReceiver(username);
        statement.setSender(null);

        statementRepository.save(statement);
    }

    @Override
    public void transfer(String sender, String receiver, int amount) {
        Optional<User> from = userRepository.findByUsername(sender);
        Optional<User> to = userRepository.findByUsername(receiver);

        if (from.isPresent() && to.isPresent()) {
            User fromUser = from.get();
            User toUser = to.get();

            fromUser.setBalance(fromUser.getBalance() - amount);
            toUser.setBalance(toUser.getBalance() + amount);

            userRepository.save(toUser);
            userRepository.save(fromUser);

            Statement statement = new Statement();
            statement.setType("Transfer");
            statement.setAmount(amount);
            statement.setDate(LocalDateTime.now());
            statement.setReceiver(receiver);
            statement.setSender(sender);

            statementRepository.save(statement);
        }
    }

    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

}
