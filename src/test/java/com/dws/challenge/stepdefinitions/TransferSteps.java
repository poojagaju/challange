package com.dws.challenge.stepdefinitions;

import com.dws.challenge.config.CucumberSpringConfiguration;
import com.dws.challenge.model.Account;
import com.dws.challenge.model.TransactionDetails;
import com.dws.challenge.model.TransferCompletionInfo;
import com.dws.challenge.service.TransferManagementService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
@SpringBootTest
@ContextConfiguration(classes = { CucumberSpringConfiguration.class })
public class TransferSteps {

    @Autowired
    private TransferManagementService transferManagementService;

    private Account fromAccount;
    private Account toAccount;
    private String errorMessage;

    @Given("an account with id {string} and balance {int}")
    public void givenAnAccountWithIdAndBalance(String accountId, int balance) {
        if ("123".equals(accountId)) {
            fromAccount = new Account(accountId, BigDecimal.valueOf(balance));
        } else if ("456".equals(accountId)) {
            toAccount = new Account(accountId, BigDecimal.valueOf(balance));
        }
    }

    @When("I transfer {int} from account {string} to account {string}")
    public void whenITransferAmountFromAccountToAccount(int amount, String fromAccountId, String toAccountId) {
        TransactionDetails transactionDetails = new TransactionDetails(fromAccountId, toAccountId, BigDecimal.valueOf(amount));
        try {
            TransferCompletionInfo transferCompletionInfo = transferManagementService.transfer(transactionDetails);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the balance of account {string} should be {int}")
    public void thenTheBalanceOfAccountShouldBe(String accountId, int expectedBalance) {
        Account account = ("123".equals(accountId)) ? fromAccount : toAccount;
        Assertions.assertEquals(BigDecimal.valueOf(expectedBalance), account.getBalance(), "Balance mismatch");
    }

    @Then("the transfer should fail with an error message {string}")
    public void thenTheTransferShouldFailWithErrorMessage(String expectedErrorMessage) {
        Assertions.assertEquals(expectedErrorMessage, errorMessage, "Error message mismatch");
    }
}