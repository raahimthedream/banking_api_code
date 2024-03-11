package com.banking.api.bankin_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.api.bankin_project.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
