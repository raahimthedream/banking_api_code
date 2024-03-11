package com.banking.api.bankin_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.api.bankin_project.model.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>  {

	List<Transaction> findByBankAccountId(Long accountId);
}
