package com.dws.challenge.repository;

import com.dws.challenge.exception.InsufficientFundsException;
import com.dws.challenge.exception.InvalidTransactionAmountException;
import com.dws.challenge.model.Account;
import com.dws.challenge.repository.impl.AccountsInfoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountsInfoRepositoryImplTest {

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountsInfoRepositoryImpl accountsInfoRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("1", BigDecimal.valueOf(1000));
    }

    @Test
    void testCreditSuccessful() {
        // Arrange
        when(accountsRepository.getAccount("1")).thenReturn(account);
        BigDecimal creditAmount = BigDecimal.valueOf(500);

        // Act
        accountsInfoRepository.credit("1", creditAmount);

        // Assert
        assertEquals(BigDecimal.valueOf(1500), account.getBalance());
        verify(accountsRepository, times(1)).getAccount("1");
    }

    @Test
    void testDebitSuccessful() {
        // Arrange
        when(accountsRepository.getAccount("1")).thenReturn(account);
        BigDecimal debitAmount = BigDecimal.valueOf(500);

        // Act
        accountsInfoRepository.debit("1", debitAmount);

        // Assert
        assertEquals(BigDecimal.valueOf(500), account.getBalance());
        verify(accountsRepository, times(1)).getAccount("1");
    }

    @Test
    void testDebitInsufficientFunds() {
        // Arrange
        when(accountsRepository.getAccount("1")).thenReturn(account);
        BigDecimal debitAmount = BigDecimal.valueOf(1500);

        // Act & Assert
        InsufficientFundsException thrown = assertThrows(InsufficientFundsException.class,
                () -> accountsInfoRepository.debit("1", debitAmount));

        assertEquals("Account 1 has funds 1000 which are insufficient to complete the transaction for amount 1500", thrown.getMessage());
    }

    @Test
    void testCreditInvalidAmount() {
        // Arrange
        when(accountsRepository.getAccount("1")).thenReturn(account);
        BigDecimal invalidAmount = BigDecimal.valueOf(-100);

        // Act & Assert
        InvalidTransactionAmountException thrown = assertThrows(InvalidTransactionAmountException.class,
                () -> accountsInfoRepository.credit("1", invalidAmount));

        assertEquals("Transaction with amount -100 not allowed", thrown.getMessage());
    }

    @Test
    void testDebitInvalidAmount() {
        // Arrange
        when(accountsRepository.getAccount("1")).thenReturn(account);
        BigDecimal invalidAmount = BigDecimal.valueOf(-100);

        // Act & Assert
        InvalidTransactionAmountException thrown = assertThrows(InvalidTransactionAmountException.class,
                () -> accountsInfoRepository.debit("1", invalidAmount));

        assertEquals("Transaction with amount -100 not allowed", thrown.getMessage());
    }
}
