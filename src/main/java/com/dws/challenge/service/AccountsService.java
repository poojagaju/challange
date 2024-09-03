package com.dws.challenge.service;

import com.dws.challenge.model.Account;

public interface AccountsService {

  void createAccount(Account account);

  Account getAccount(String accountId);

}
