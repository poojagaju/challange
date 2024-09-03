package com.dws.challenge.web;

import com.dws.challenge.model.Account;
import com.dws.challenge.service.AccountsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/accounts")
@Api(value = "Accounts API", description = "APIs for creating new accounts and retrieving account information")
@Slf4j
public class AccountsController {

  private final AccountsService accountsService;


  @Autowired
  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
  }

  /**
   * Creates a new account.
   * <p>
   * This endpoint allows the creation of a new account in the system. The account information is provided
   * in the request body in JSON format. The request body must be validated and contain all necessary
   * information to create an account.
   * </p>
   *
   * @param account The account information to be created.
   * @return A {@link ResponseEntity} with HTTP status 201 (Created) if the account is created successfully.
   *         If there is an error with the input, the server responds with HTTP status 400 (Bad Request).
   *         If an internal error occurs, the server responds with HTTP status 500 (Internal Server Error).
   */
  @ApiOperation(value = "Create an account", response = ResponseEntity.class)
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "Account created successfully"),
          @ApiResponse(code = 400, message = "Invalid input provided"),
          @ApiResponse(code = 500, message = "Internal server error occurred")
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> createAccount(@RequestBody @Valid Account account) {
    log.info("Creating account: {}", account);

    accountsService.createAccount(account);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }


  /**
   * Retrieves an account by its ID.
   * <p>
   * This endpoint retrieves the details of an account identified by the account ID provided in the path variable.
   * The response will include the account details if the account exists. If the account does not exist,
   * the server responds with HTTP status 404 (Not Found). Invalid input will result in an HTTP status 400
   * (Bad Request), and any internal error will result in an HTTP status 500 (Internal Server Error).
   * </p>
   *
   * @param accountId The ID of the account to be retrieved.
   * @return A {@link ResponseEntity} containing the {@link Account} object with HTTP status 200 (OK) if the
   *         account is found. If the account does not exist, the response status is HTTP 404 (Not Found).
   *         If there is an error with the input, the response status is HTTP 400 (Bad Request).
   *         If an internal error occurs, the response status is HTTP 500 (Internal Server Error).
   */
  @ApiOperation(value = "Get an account by account ID", response = Account.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Successfully retrieved the account"),
          @ApiResponse(code = 400, message = "Invalid input provided"),
          @ApiResponse(code = 404, message = "Account not found"),
          @ApiResponse(code = 500, message = "Internal server error occurred")
  })
  @GetMapping(path = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Account> getAccount(@PathVariable String accountId) {
    log.info("Retrieving account for ID: {}", accountId);

    Account account = accountsService.getAccount(accountId);
    if (account == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(account);
  }
}
