package com.banking.api.bankin_project.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.api.bankin_project.model.BankAccount;
import com.banking.api.bankin_project.model.Customer;
import com.banking.api.bankin_project.model.Transaction;
import com.banking.api.bankin_project.model.TransactionType;
import com.banking.api.bankin_project.repository.BankAccountRepository;
import com.banking.api.bankin_project.repository.CustomerRepository;
import com.banking.api.bankin_project.repository.TransactionRepository;

@Service
public class BankServiceImpl implements BankService {
    
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository; // Import for CustomerRepository

    @Autowired
    public BankServiceImpl(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public BankAccount createAccount(Long customerId, BigDecimal initialDeposit) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setCustomer(customer);
        bankAccount.setBalance(initialDeposit);

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        BankAccount fromAccount = bankAccountRepository.findById(fromAccountId).orElseThrow(() -> new RuntimeException("From Account not found"));
        BankAccount toAccount = bankAccountRepository.findById(toAccountId).orElseThrow(() -> new RuntimeException("To Account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        Transaction debitTransaction = new Transaction();
        debitTransaction.setBankAccount(fromAccount);
        debitTransaction.setType(TransactionType.DEBIT);
        debitTransaction.setAmount(amount.negate().doubleValue());

        Transaction creditTransaction = new Transaction();
        creditTransaction.setBankAccount(toAccount);
        creditTransaction.setType(TransactionType.CREDIT);
        creditTransaction.setAmount(amount.doubleValue());

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        bankAccountRepository.saveAll(Arrays.asList(fromAccount, toAccount));
        transactionRepository.saveAll(Arrays.asList(debitTransaction, creditTransaction));
    }

    @Override
    public BigDecimal getBalance(Long accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return bankAccount.getBalance();
    }

    @Override
    public List<Transaction> getTransactionHistory(Long accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return transactionRepository.findByBankAccountId(accountId);
    }
}
