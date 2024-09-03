package com.dws.challenge.web;

import com.dws.challenge.exception.InvalidSelfTransferException;
import com.dws.challenge.model.TransferCompletionInfo;
import com.dws.challenge.model.TransactionDetails;
import com.dws.challenge.service.TransferManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferControllerTest {

    @Mock
    private TransferManagementService transferManagementService;

    @InjectMocks
    private TransferController transferController;

    @BeforeEach
    public void setup() {
        // No need for MockitoAnnotations.openMocks(this) with @ExtendWith
    }

    @Test
    void testTransferSuccessful() {
        // Prepare test data
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setFrom("account1");
        transactionDetails.setTo("account2");
        transactionDetails.setAmount(BigDecimal.valueOf(100.0));

        TransferCompletionInfo transferCompletionInfo = new TransferCompletionInfo();

        when(transferManagementService.transfer(any(TransactionDetails.class)))
                .thenReturn(transferCompletionInfo);

        // Execute test
        ResponseEntity<TransferCompletionInfo> response = transferController.transfer(transactionDetails);

        // Verify results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferCompletionInfo, response.getBody());
        verify(transferManagementService, times(1))
                .transfer(eq(transactionDetails));
    }


    @Test
    public void testTransferSelfTransferException() {
        // Prepare test data
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setFrom("account1");
        transactionDetails.setTo("account1");
        transactionDetails.setAmount(BigDecimal.valueOf(100.0));

        // Execute test
        Assertions.assertThrows(InvalidSelfTransferException.class,
                () -> transferController.transfer(transactionDetails));

        // Verify results
        Mockito.verify(transferManagementService, Mockito.never())
                .transfer(Mockito.any(TransactionDetails.class));
    }
}
