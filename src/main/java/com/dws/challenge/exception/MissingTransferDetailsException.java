package com.dws.challenge.exception;

public class MissingTransferDetailsException extends RuntimeException {

    public MissingTransferDetailsException(String message) {
        super(message);
    }
}