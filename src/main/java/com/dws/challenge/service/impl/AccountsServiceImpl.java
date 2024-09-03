package com.dws.challenge.service.impl;

import com.dws.challenge.model.Account;
import com.dws.challenge.repository.AccountsRepository;
import com.dws.challenge.service.AccountsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class AccountsServiceImpl implements AccountsService {
    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void createAccount(Account account) {
        accountsRepository.createAccount(account);
    }

    @Override
    public Account getAccount(String accountId) {
        return accountsRepository.getAccount(accountId);
    }
}
