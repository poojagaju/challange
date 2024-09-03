package com.dws.challenge.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * Represents the details of an account transfer after completion.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TransferCompletionInfo {

    private String to;
    private BigDecimal toBalance;
    private String from;
    private BigDecimal fromBalance;
}
