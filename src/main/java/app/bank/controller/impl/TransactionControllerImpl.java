package app.bank.controller.impl;

import app.bank.controller.TransactionController;
import app.bank.service.TransactionService;
import app.bank.service.impl.JwtService;
import app.bank.service.impl.TransactionServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/bank/user")
public class TransactionControllerImpl implements TransactionController {

    private TransactionService transactionService;
    private JwtService jwtService;

    public TransactionControllerImpl(TransactionService transactionService, JwtService jwtService) {
        this.jwtService = jwtService;
        this.transactionService = transactionService;
    }

    @Override
    @PutMapping(path = "/cashIn")
    public void cashIn(@RequestHeader("Authorization") String token, @RequestParam int amount) {
        String jwt = token.substring(7);
        String username = jwtService.extractUserName(jwt);
        transactionService.cashIn(username, amount);
    }

    @Override
    @PutMapping(path = "/cashOut")
    public void cashOut(@RequestHeader("Authorization") String token, @RequestParam int amount) {
        String jwt = token.substring(7);
        String username = jwtService.extractUserName(jwt);
        transactionService.cashOut(username, amount);
    }

    @Override
    @PutMapping(path = "/transfer")
    public void transfer(@RequestHeader("Authorization") String token, @RequestParam String receiver, @RequestParam int amount) {
        String jwt = token.substring(7);
        String sender = jwtService.extractUserName(jwt);
        transactionService.transfer(sender, receiver, amount);
    }
}
