package com.dws.challenge.repository.impl;

import com.dws.challenge.model.Account;
import com.dws.challenge.exception.InsufficientFundsException;
import com.dws.challenge.exception.InvalidTransactionAmountException;
import com.dws.challenge.repository.AccountsInfoRepository;
import com.dws.challenge.repository.AccountsRepository;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Getter
@Repository
public class AccountsInfoRepositoryImpl implements AccountsInfoRepository {
    private final AccountsRepository accountsRepository;

    public AccountsInfoRepositoryImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void credit(String id, BigDecimal amount) {
        Account account = accountsRepository.getAccount(id);
        validateAmount(amount);
        account.setBalance(account.getBalance().add(amount));

    }

    @Override
    public void debit(String id, BigDecimal amount) {
        Account account = accountsRepository.getAccount(id);
        validateAmount(amount);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                    String.format(
                            "Account %s has funds %s which are insufficient to complete the transaction for amount %s",
                            id, account.getBalance(), amount
                    )
            );
        }
        account.setBalance(account.getBalance().subtract(amount));
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || BigDecimal.ZERO.compareTo(amount) > 0) {
            throw new InvalidTransactionAmountException(String.format("Transaction with amount %s not allowed", amount));
        }
    }

}
