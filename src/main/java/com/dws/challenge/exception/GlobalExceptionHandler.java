package com.dws.challenge.exception;

import com.dws.challenge.model.ErrorDetails;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTransactionAmountException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDetails handleInvalidTransactionAmountException(final InvalidTransactionAmountException exception,
                                                                        final HttpServletRequest request) {

        ErrorDetails error = new ErrorDetails();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());

        return error;
    }

    @ExceptionHandler(MissingTransferDetailsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDetails handleMissingTransferDetailsException(final MissingTransferDetailsException exception,
                                                                                    final HttpServletRequest request) {

        ErrorDetails error = new ErrorDetails();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());

        return error;
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDetails handleInsufficientBalanceException(final InsufficientBalanceException exception,
                                                                              final HttpServletRequest request) {

        ErrorDetails error = new ErrorDetails();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());

        return error;
    }
    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDetails handleInsufficientFundsException(final InsufficientFundsException exception,
                                                                            final HttpServletRequest request) {

        ErrorDetails error = new ErrorDetails();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());

        return error;
    }
    @ExceptionHandler(InvalidSelfTransferException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDetails handleInvalidSelfTransferException(final InvalidSelfTransferException exception,
                                                                   final HttpServletRequest request) {

        ErrorDetails error = new ErrorDetails();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());

        return error;
    }

    @ExceptionHandler(AccountAlreadyPresentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDetails handleAccountAlreadyPresentException(final AccountAlreadyPresentException exception,
                                                                               final HttpServletRequest request) {

        ErrorDetails error = new ErrorDetails();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());

        return error;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorDetails handleAccountNotFoundException(final AccountNotFoundException exception,
                                                                              final HttpServletRequest request) {

        ErrorDetails error = new ErrorDetails();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());

        return error;
    }

    @ExceptionHandler(DuplicateAccountIdException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody ErrorDetails handleDuplicateAccountIdException(final DuplicateAccountIdException exception,
                                                                             final HttpServletRequest request) {

        ErrorDetails error = new ErrorDetails();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());

        return error;
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDetails handleException(final Exception exception,
                                                           final HttpServletRequest request) {

        ErrorDetails error = new ErrorDetails();
        error.setErrorMessage(exception.getMessage());
        error.setRequestedURI(request.getRequestURI());

        return error;
    }
}