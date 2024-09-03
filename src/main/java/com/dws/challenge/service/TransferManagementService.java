package com.dws.challenge.service;

import com.dws.challenge.model.Account;
import com.dws.challenge.model.TransactionDetails;
import com.dws.challenge.model.TransferCompletionInfo;

public interface TransferManagementService {
    void doTransfer(Account fromAccount, Account toAccount, TransferCompletionInfo transferCompletionInfo, TransactionDetails transactionDetails );
    TransferCompletionInfo transfer(TransactionDetails transactionDetails);
}
