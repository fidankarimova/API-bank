package app.bank.service;

public interface TransactionService {
    void cashIn(String username, int amount);
    void cashOut(String username, int amount);
    void transfer(String sender, String receiver, int amount);
}
