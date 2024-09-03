package com.dws.challenge.repository;
import java.math.BigDecimal;

public interface AccountsInfoRepository {
    void credit(String id, BigDecimal amount);
    void debit(String id, BigDecimal amount);
}
