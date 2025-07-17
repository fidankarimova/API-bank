package app.bank.controller;

import app.bank.exception.InvalidUserInput;
import app.bank.exception.NotEnoughMoney;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidUserInput.class)
    public ResponseEntity<String> handleInvalidUserInput(InvalidUserInput ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughMoney.class)
    public ResponseEntity<String> handleNotEnoughMoney(NotEnoughMoney ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
