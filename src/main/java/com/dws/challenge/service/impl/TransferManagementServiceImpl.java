package com.dws.challenge.service.impl;

import com.dws.challenge.model.Account;
import com.dws.challenge.model.TransactionDetails;
import com.dws.challenge.model.TransferCompletionInfo;
import com.dws.challenge.repository.AccountsInfoRepository;
import com.dws.challenge.repository.AccountsRepository;
import com.dws.challenge.service.TransferManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransferManagementServiceImpl implements TransferManagementService {

    @Autowired
    private final AccountsRepository accountsRepository;

    @Autowired
    private final AccountsInfoRepository accountsInfoRepository;

    @Autowired
    private final EmailNotificationService notificationService;

    public TransferManagementServiceImpl(AccountsRepository accountsRepository, AccountsInfoRepository accountsInfoRepository, EmailNotificationService notificationService) {
        this.accountsRepository = accountsRepository;
        this.accountsInfoRepository = accountsInfoRepository;
        this.notificationService = notificationService;
    }

    /**
     * Transfering the amount based on requirement.
     *
     * @return transfer info.
     */
    public TransferCompletionInfo transfer(TransactionDetails transactionDetails) {

        Account fromAccount = accountsRepository.getAccount(transactionDetails.getFrom());
        Account toAccount = accountsRepository.getAccount(transactionDetails.getTo());

        /* construct the transferCompletionDetails outside
            of the synchronized blocks for minimalistic locking.
         */
        TransferCompletionInfo transferCompletionInfo = new TransferCompletionInfo();
        transferCompletionInfo.setFrom(fromAccount.getAccountId());
        transferCompletionInfo.setTo(toAccount.getAccountId());

        /*
            compare the accountIds of fromAccount and toAccount to determine the order
            of locking. This ensures that there's an order in which the objects are locked
            so that the "Cyclic Wait" condition never occurs. The account with lower id has
            higher priority and would need to be locked first. The "from" account and "to" account
            can't have same id as in this case the validate method throws SelfTransferException.
         */
        if (transactionDetails.getFrom().compareTo(transactionDetails.getTo()) > 0) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    doTransfer(fromAccount,toAccount,transferCompletionInfo,transactionDetails);
                }
            }
        } else {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    doTransfer(fromAccount,toAccount,transferCompletionInfo,transactionDetails);
                }
            }
        }



        return transferCompletionInfo;
    }

    public void doTransfer(Account fromAccount, Account toAccount,TransferCompletionInfo transferCompletionInfo,TransactionDetails transactionDetails ){
        accountsInfoRepository.debit(fromAccount.getAccountId(), transactionDetails.getAmount());
        accountsInfoRepository.credit(toAccount.getAccountId(), transactionDetails.getAmount());
        transferCompletionInfo.setFromBalance(fromAccount.getBalance());
        transferCompletionInfo.setToBalance(toAccount.getBalance());
        notificationService.notifyAboutTransfer(fromAccount,"Transfer of " + transactionDetails.getAmount() + " to account " + toAccount.getAccountId());
        notificationService.notifyAboutTransfer(toAccount, "Transfer of " + transactionDetails.getAmount() + " from account " + fromAccount.getAccountId());
    }

}
