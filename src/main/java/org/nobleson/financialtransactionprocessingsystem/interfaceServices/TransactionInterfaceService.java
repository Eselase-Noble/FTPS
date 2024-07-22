package org.nobleson.financialtransactionprocessingsystem.interfaceServices;

import org.nobleson.financialtransactionprocessingsystem.models.Transaction;

import java.math.BigDecimal;

public interface TransactionInterfaceService {

    void createTransaction(Long transactionId);
    void withdraw(String accountNumber, BigDecimal amount);
    void deposit(String accountNumber, BigDecimal amount);
    void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount);
    BigDecimal getMainBalance(String accountNumber);
    BigDecimal getAvailableBalance(String accountNumber);
}
