package app.bank.service.impl;

import app.bank.entity.User;
import app.bank.repository.StatementRepository;
import app.bank.repository.UserRepository;
import app.bank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    private UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository, StatementRepository statementRepository) {
        this.userRepository=userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
