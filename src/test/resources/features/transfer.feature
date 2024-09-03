Feature: Transfer funds between accounts

  Scenario: Successful transfer
    Given an account with id "123" and balance 1000
    And an account with id "456" and balance 500
    When I transfer 200 from account "123" to account "456"
    Then the balance of account "123" should be 800
    And the balance of account "456" should be 700

  Scenario: Transfer failure due to insufficient funds
    Given an account with id "123" and balance 100
    And an account with id "456" and balance 500
    When I transfer 200 from account "123" to account "456"
    Then the transfer should fail with an error message "Insufficient funds"
