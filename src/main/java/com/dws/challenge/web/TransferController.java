package com.dws.challenge.web;

import com.dws.challenge.exception.InvalidSelfTransferException;
import com.dws.challenge.exception.MissingTransferDetailsException;
import com.dws.challenge.model.TransferCompletionInfo;
import com.dws.challenge.model.TransactionDetails;
import com.dws.challenge.service.TransferManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/transfers")
@Slf4j
public class TransferController {

    private final TransferManagementService transferManagementService;

    public TransferController(TransferManagementService transferManagementService) {
        this.transferManagementService = transferManagementService;
    }

    /**
     * Processes a money transfer between two accounts
     *
     * @param transactionDetails
     * @return
     */
    @Operation(summary = "Transfer money between two accounts",
            description = "Processes a money transfer between two accounts and returns the details of the completed transfer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully transferred the money",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransferCompletionInfo.class))),
            @ApiResponse(responseCode = "400", description = "Invalid or incomplete transfer details provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransferCompletionInfo> transfer(@RequestBody @Valid TransactionDetails transactionDetails) {
        validate(transactionDetails);

        log.info("Processing transfer request: {}", transactionDetails);
        TransferCompletionInfo result = transferManagementService.transfer(transactionDetails);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * This is helper method for validating transactiondetails.
     *
     * @param transactionDetails
     */
    private void validate(TransactionDetails transactionDetails) {
        if (transactionDetails.getFrom() == null) {
            throw new MissingTransferDetailsException("Missing required attribute 'from account id'");
        }
        if (transactionDetails.getTo() == null) {
            throw new MissingTransferDetailsException("Missing required attribute 'to account id'");
        }
        if (transactionDetails.getAmount() == null) {
            throw new MissingTransferDetailsException("Missing required attribute 'transfer amount'");
        }
        if (transactionDetails.getTo().equals(transactionDetails.getFrom())) {
            throw new InvalidSelfTransferException("From accountId and To accountId can't be the same");
        }
    }
}
