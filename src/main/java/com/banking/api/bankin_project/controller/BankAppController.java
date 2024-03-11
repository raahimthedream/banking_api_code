package com.banking.api.bankin_project.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.api.bankin_project.model.BankAccount;
import com.banking.api.bankin_project.service.BankService;

@RestController
@RequestMapping("/api/v1")
public class BankAppController {
	private final BankService bankService;

	@Autowired
	public BankAppController(BankService bankService) {
		this.bankService = bankService;
	}

	@PostMapping("/customers/{customerId}/accounts")
	public ResponseEntity<BankAccount> createAccount(@PathVariable Long customerId,
			@RequestBody BigDecimal initialDeposit) {
		BankAccount bankAccount = bankService.createAccount(customerId, initialDeposit);
		return new ResponseEntity<BankAccount>(bankAccount, HttpStatus.CREATED);
	}

	@PostMapping("/accounts/{fromAccountId}/transfer")
	public ResponseEntity<Void> transfer(@PathVariable Long fromAccountId, @RequestParam Long toAccountId,
			@RequestParam BigDecimal amount) {
		bankService.transfer(fromAccountId, toAccountId, amount);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/accounts/{accountId}/balance")
	public BigDecimal getBalance(@PathVariable Long accountId) {
		return bankService.getBalance(accountId);
	}
}