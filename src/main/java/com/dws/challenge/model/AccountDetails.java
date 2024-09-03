package com.dws.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents the details required to create a new account.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetails {

    private String id;
    private BigDecimal balance;
}
