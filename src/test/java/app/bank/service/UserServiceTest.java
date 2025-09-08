package app.bank.service;

import app.bank.entity.User;
import app.bank.exception.InvalidUserInput;
import app.bank.exception.NotEnoughMoney;
import app.bank.repository.StatementRepository;
import app.bank.repository.UserRepository;
import app.bank.service.impl.AdminServiceImpl;
import app.bank.service.impl.TransactionServiceImpl;
import app.bank.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private StatementRepository statementRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @InjectMocks
    private AdminServiceImpl adminServiceImpl;

    @Test
    void cashIn_Success() {
        String username = "user";
        int initialBalance = 100;
        int amount = 100;

        User mockUser = new User();

        mockUser.setUsername(username);
        mockUser.setBalance(initialBalance);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        transactionServiceImpl.cashIn(username, amount);

        assertEquals(200, mockUser.getBalance());
        Mockito.verify(userRepository).save(mockUser);
        Mockito.verify(statementRepository).save(any());
    }

    @Test
    void cashIn_NegativeInput_ThrowsException() {
        String username = "user";
        int amount = -50;
        assertThrows(InvalidUserInput.class, () -> transactionServiceImpl.cashIn(username, amount));
        verifyNoInteractions(userRepository);
        verifyNoInteractions(statementRepository);
    }

    @Test
    void cashOut_Success() {
        String username = "user";
        int initialBalance = 100;
        int amount = 50;

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setBalance(initialBalance);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        transactionServiceImpl.cashOut(username, amount);

        assertEquals(50, mockUser.getBalance());
        verify(userRepository).save(mockUser);
        verify(statementRepository).save(any());
    }

    @Test
    void cashOut_NegativeInput_ThrowsException() {
        String username = "user";
        int amount = -50;
        assertThrows(InvalidUserInput.class, () -> transactionServiceImpl.cashOut(username, amount));
        verifyNoInteractions(userRepository);
        verifyNoInteractions(statementRepository);
    }

    @Test
    void cashOut_InsufficientBalance_ThrowsException() {
        String username = "user";
        int amount = 100;

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setBalance(50);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        assertThrows(NotEnoughMoney.class, () -> transactionServiceImpl.cashOut(username, amount));

        verify(userRepository, never()).save(any());
        verify(statementRepository, never()).save(any());
    }


    @Test
    void transfer_Successt() {
        String sender = "sender";
        String receiver = "receiver";
        int amount = 50;

        User mockSender = new User();
        mockSender.setUsername(sender);
        mockSender.setBalance(200);

        User mockReceiver = new User();
        mockReceiver.setUsername(receiver);
        mockReceiver.setBalance(200);

        when(userRepository.findByUsername(sender)).thenReturn(Optional.of(mockSender));
        when(userRepository.findByUsername(receiver)).thenReturn(Optional.of(mockReceiver));

        transactionServiceImpl.transfer(sender, receiver, amount);
        assertEquals(150, mockSender.getBalance());
        assertEquals(250, mockReceiver.getBalance());

        verify(userRepository).save(mockSender);
        verify(userRepository).save(mockReceiver);
        verify(statementRepository).save(any());
    }
}
