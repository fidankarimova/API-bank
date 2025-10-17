package app.bank.service.impl;

import app.bank.entity.Statement;
import app.bank.entity.User;
import app.bank.exception.NotEnoughMoney;
import app.bank.exception.UserNotFound;
import app.bank.repository.StatementRepository;
import app.bank.repository.UserRepository;
import app.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserRepository userRepository;
    private final StatementRepository statementRepository;

    @Override
    public void cashIn(String username, int amount) {
//        if (amount < 0) {
//            throw new InvalidUserInput("Invalid input");
//        }
//
//        Optional<User> optional = userRepository.findByUsername(username);
//        if (optional.isPresent()) {
//            User user = optional.get();
//            int balance = user.getBalance();
//            balance += amount;
//            user.setBalance(balance);
//            userRepository.save(user);
//        }
//
//        Statement statement = new Statement();
//        statement.setType("Cash in");
//        statement.setAmount(amount);
//        statement.setDate(LocalDateTime.now());
//        statement.setReceiver(username);
//        statement.setSender(null);
//
//        statementRepository.save(statement);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFound("User not found"));

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        Statement statement = new Statement();
        statement.setType("Cash in");
        statement.setAmount(amount);
        statement.setDate(LocalDateTime.now());
        statement.setReceiver(user);
        statement.setSender(null);

        statementRepository.save(statement);

    }

    @Override
    public void cashOut(String username, int amount) {
//        if (amount < 0) {
//            throw new InvalidUserInput("Invalid input");
//        }
//
//        Optional<User> optional = userRepository.findByUsername(username);
//        if(optional.isEmpty()) {
//            throw new UserNotFound("User not found");
//        }
//        User user = optional.get();
//
//        if(user.getBalance() < amount) {
//            throw new NotEnoughMoney("Not enough money");
//        }
//
//        int balance = user.getBalance();
//        balance -= amount;
//        user.setBalance(balance);
//        userRepository.save(user);
//
//
//        Statement statement = new Statement();
//        statement.setType("Cash out");
//        statement.setAmount(amount);
//        statement.setDate(LocalDateTime.now());
//        statement.setReceiver(username);
//        statement.setSender(null);
//
//        statementRepository.save(statement);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFound("User not found"));

        if (user.getBalance() < amount) {
            throw new NotEnoughMoney("Not enough money");
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        Statement statement = new Statement();
        statement.setType("Cash out");
        statement.setAmount(amount);
        statement.setDate(LocalDateTime.now());
        statement.setSender(user);
        statement.setReceiver(null);

        statementRepository.save(statement);

    }

    @Override
    public void transfer(String sender, String receiver, int amount) {
//        if (amount < 0) {
//            throw new InvalidUserInput("Invalid input");
//        }
//        Optional<User> from = userRepository.findByUsername(sender);
//        Optional<User> to = userRepository.findByUsername(receiver);
//
//        if (from.isEmpty() || to.isEmpty()) {
//            throw new UserNotFound("User not found");
//        }
//
//        User fromUser = from.get();
//        User toUser = to.get();
//
//        if(fromUser.getBalance() < amount) {
//            throw new NotEnoughMoney("Not enough money");
//        }
//
//        fromUser.setBalance(fromUser.getBalance() - amount);
//        toUser.setBalance(toUser.getBalance() + amount);
//
//        userRepository.save(toUser);
//        userRepository.save(fromUser);
//
//        Statement statement = new Statement();
//        statement.setType("Transfer");
//        statement.setAmount(amount);
//        statement.setDate(LocalDateTime.now());
//        statement.setReceiver(receiver);
//        statement.setSender(sender);
//        statementRepository.save(statement);
        User fromUser = userRepository.findByUsername(sender)
                .orElseThrow(() -> new UserNotFound("Sender not found"));
        User toUser = userRepository.findByUsername(receiver)
                .orElseThrow(() -> new UserNotFound("Receiver not found"));

        if (fromUser.getBalance() < amount) {
            throw new NotEnoughMoney("Not enough money");
        }

        fromUser.setBalance(fromUser.getBalance() - amount);
        toUser.setBalance(toUser.getBalance() + amount);

        userRepository.save(fromUser);
        userRepository.save(toUser);

        Statement statement = new Statement();
        statement.setType("Transfer");
        statement.setAmount(amount);
        statement.setDate(LocalDateTime.now());
        statement.setSender(fromUser);
        statement.setReceiver(toUser);

        statementRepository.save(statement);

    }
}
