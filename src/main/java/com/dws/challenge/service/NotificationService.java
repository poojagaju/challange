package com.dws.challenge.service;

import com.dws.challenge.model.Account;

public interface NotificationService {

  void notifyAboutTransfer(Account account, String transferDescription);
}
