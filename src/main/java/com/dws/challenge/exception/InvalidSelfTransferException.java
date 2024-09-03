package com.dws.challenge.exception;

public class InvalidSelfTransferException extends RuntimeException {
    public InvalidSelfTransferException(String message) {
        super(message);
    }
}
