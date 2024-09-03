package com.dws.challenge.exception;

public class AccountAlreadyPresentException extends RuntimeException {
    public AccountAlreadyPresentException(String message) {
        super(message);
    }
}
