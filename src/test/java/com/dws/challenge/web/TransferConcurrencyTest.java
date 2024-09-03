package com.dws.challenge.web;

import com.dws.challenge.model.TransferCompletionInfo;
import com.dws.challenge.model.TransactionDetails;
import com.dws.challenge.service.TransferManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.ExecutorService;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TransferConcurrencyTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TransferController transferController;

    @Mock
    private TransferManagementService transferManagementService;

    @Test
    public void testTransferConcurrency() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            TransactionDetails transactionDetails = new TransactionDetails();
            transactionDetails.setFrom("from-account-" + i);
            transactionDetails.setTo("to-account-" + i);
            transactionDetails.setAmount(new BigDecimal(100.0));

            Runnable task = () -> {
                ResponseEntity<TransferCompletionInfo> responseEntity =
                        transferController.transfer(transactionDetails);

                assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            };

            executorService.submit(task);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

}
