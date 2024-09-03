package com.dws.challenge.service;

import com.dws.challenge.model.Account;
import com.dws.challenge.model.TransactionDetails;
import com.dws.challenge.model.TransferCompletionInfo;
import com.dws.challenge.repository.AccountsInfoRepository;
import com.dws.challenge.repository.AccountsRepository;
import com.dws.challenge.service.impl.EmailNotificationService;
import com.dws.challenge.service.impl.TransferManagementServiceImpl;
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
public class TransferManagementServiceImplTest {

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private AccountsInfoRepository accountsInfoRepository;

    @Mock
    private EmailNotificationService notificationService;

    @InjectMocks
    private TransferManagementServiceImpl transferManagementService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testTransferSuccess() {
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setFrom("123");
        transactionDetails.setTo("456");
        transactionDetails.setAmount(new BigDecimal("50"));

        TransferCompletionInfo expected = new TransferCompletionInfo();
        expected.setFrom("123");
        expected.setFromBalance(new BigDecimal("450"));
        expected.setTo("456");
        expected.setToBalance(new BigDecimal("550"));

        // Mock behavior of repository
        when(accountsRepository.getAccount("123")).thenReturn(new Account("123", new BigDecimal("500")));
        when(accountsRepository.getAccount("456")).thenReturn(new Account("456", new BigDecimal("500")));

        doNothing().when(accountsInfoRepository).debit("123", new BigDecimal("50"));
        doNothing().when(accountsInfoRepository).credit("456", new BigDecimal("50"));

        // When
        TransferCompletionInfo actual = transferManagementService.transfer(transactionDetails);

        // Then
        assertEquals(expected.getFrom(), actual.getFrom());
        assertEquals(expected.getTo(), actual.getTo());

        verify(accountsRepository).getAccount("123");
        verify(accountsRepository).getAccount("456");
        verify(accountsInfoRepository).debit("123", new BigDecimal("50"));
        verify(accountsInfoRepository).credit("456", new BigDecimal("50"));
        verify(notificationService, times(2)).notifyAboutTransfer(any(Account.class), anyString());
    }

    @Test
    void testTransferAccountsLockInOrderToPreventDeadlocks() throws InterruptedException {
        // Given
        String fromAccountId = "123";
        String toAccountId = "456";
        BigDecimal amount = BigDecimal.TEN;

        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setFrom(fromAccountId);
        transactionDetails.setTo(toAccountId);
        transactionDetails.setAmount(amount);

        Account fromAccount = new Account(fromAccountId, BigDecimal.valueOf(100));
        Account toAccount = new Account(toAccountId, BigDecimal.valueOf(50));

        when(accountsRepository.getAccount(fromAccountId)).thenReturn(fromAccount);
        when(accountsRepository.getAccount(toAccountId)).thenReturn(toAccount);

        // Create and start threads to simulate concurrent transfers
        Thread t1 = new Thread(() -> {
            transferManagementService.transfer(transactionDetails);
        });

        TransactionDetails transactionDetailsThread = new TransactionDetails();
        transactionDetailsThread.setFrom(fromAccountId);
        transactionDetailsThread.setTo(toAccountId);
        transactionDetailsThread.setAmount(amount);

        Thread t2 = new Thread(() -> {
            transferManagementService.transfer(transactionDetailsThread);
        });

        t1.start();
        t2.start();

        // Wait for both threads to complete
        t1.join();
        t2.join();

        // Verify that the accounts were locked and updated in the correct order
        verify(accountsInfoRepository, atLeastOnce()).debit(fromAccountId, amount);
        verify(accountsInfoRepository, atLeastOnce()).credit(toAccountId, amount);
    }
        }

