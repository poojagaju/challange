package com.dws.challenge.repository;

import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.model.Account;
import com.dws.challenge.repository.impl.AccountsRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountsRepositoryInMemoryTest {
    @Mock
    private AccountsRepositoryInMemory accountsRepository;

    @BeforeEach
    void setUp() {
        accountsRepository = new AccountsRepositoryInMemory();
    }

    @Test
    void testCreateAccountSuccessfully() throws DuplicateAccountIdException {
        // Arrange
        Account account = new Account("1", BigDecimal.valueOf(1000));

        // Act
        accountsRepository.createAccount(account);

        // Assert
        assertEquals(account, accountsRepository.getAccount("1"));
    }

    @Test
    void testCreateAccountDuplicateId() throws DuplicateAccountIdException {
        // Arrange
        Account account = new Account("1", BigDecimal.valueOf(1000));
        accountsRepository.createAccount(account);

        // Act & Assert
        DuplicateAccountIdException thrown = assertThrows(DuplicateAccountIdException.class, () -> {
            accountsRepository.createAccount(new Account("1", BigDecimal.valueOf(500)));
        });

        assertEquals("Account id 1 already exists!", thrown.getMessage());
    }

    @Test
    void testGetAccount() throws DuplicateAccountIdException {
        // Arrange
        Account account = new Account("1", BigDecimal.valueOf(1000));
        accountsRepository.createAccount(account);

        // Act
        Account retrievedAccount = accountsRepository.getAccount("1");

        // Assert
        assertEquals(account, retrievedAccount);
    }

    @Test
    void testGetAccountNotFound() {
        // Act
        Account retrievedAccount = accountsRepository.getAccount("non-existing-id");

        // Assert
        assertNull(retrievedAccount);
    }

    @Test
    void testClearAccounts() throws DuplicateAccountIdException {
        // Arrange
        Account account1 = new Account("1", BigDecimal.valueOf(1000));
        Account account2 = new Account("2", BigDecimal.valueOf(2000));
        accountsRepository.createAccount(account1);
        accountsRepository.createAccount(account2);

        // Act
        accountsRepository.clearAccounts();

        // Assert
        assertNull(accountsRepository.getAccount("1"));
        assertNull(accountsRepository.getAccount("2"));
    }
}
