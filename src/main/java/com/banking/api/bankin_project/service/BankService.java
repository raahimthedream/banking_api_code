package com.banking.api.bankin_project.service;

import java.math.BigDecimal;
import java.util.List;

import com.banking.api.bankin_project.model.BankAccount;
import com.banking.api.bankin_project.model.Transaction;


public interface BankService {
    BankAccount createAccount(Long customerId, BigDecimal initialDeposit);

    void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount);

    BigDecimal getBalance(Long accountId);

    List<Transaction> getTransactionHistory(Long accountId);
}
