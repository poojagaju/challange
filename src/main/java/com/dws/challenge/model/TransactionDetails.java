package com.dws.challenge.model;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Represents a request to transfer money between accounts.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetails {
    @NotNull
    @NotEmpty
    private String from;

    @NotNull
    @NotEmpty
    private String to;

    @NotNull
    @Min(value = 0, message = "Amount must be positive.")
    private BigDecimal amount;
}

