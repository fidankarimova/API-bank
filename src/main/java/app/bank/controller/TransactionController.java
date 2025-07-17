package app.bank.controller;

public interface TransactionController {
    void cashIn(String token, int amount);
    void cashOut(String token, int amount);
    void transfer(String token, String receiver, int amount);
}
