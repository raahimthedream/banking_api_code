package com.banking.api.bankin_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banking.api.bankin_project.controller.BankAppController;
import com.banking.api.bankin_project.model.BankAccount;
import com.banking.api.bankin_project.model.Customer;
import com.banking.api.bankin_project.repository.BankAccountRepository;
import com.banking.api.bankin_project.repository.CustomerRepository;
import com.banking.api.bankin_project.service.BankService;

@SpringBootTest
public class BankinProjectApplicationTests {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private BankService bankService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAccount_shouldCreateNewAccountWithInitialDeposit() {
        // arrange
        Long customerId = 1L;
        BigDecimal initialDeposit = new BigDecimal("1000.00");
        Customer customer = new Customer();
        customer.setId(customerId);
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(customer));
        Mockito.when(bankAccountRepository.save(Mockito.any())).thenReturn(new BankAccount());

        // act
        BankAccount result = bankService.createAccount(customerId, initialDeposit);

        // assert
        Assertions.assertNotNull(result);
        Mockito.verify(bankAccountRepository).save(Mockito.any());
        Mockito.verify(customerRepository).findById(Mockito.anyLong());
        Assertions.assertEquals(initialDeposit, result.getBalance());
    }

    @Test
    void testTransfer() {
        // arrange
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal(500);

        BankService bankService = mock(BankService.class);

        BankAppController controller = new BankAppController(bankService);

        // act
        ResponseEntity<Void> responseEntity = controller.transfer(fromAccountId, toAccountId, amount);

        // assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

//    @Test
//    void testGetBalance() {
//        // arrange
//        Long accountId = 1L;
//        BigDecimal balance = new BigDecimal(500);
//
//        BankService bankService = mock(BankService.class);
//        when(bankService.getBalance(any(Long.class))).thenReturn(balance);
//
//        BankAppController controller = new BankAppController(bankService);
//
//        // act
//        BigDecimal result = controller.getBalance(accountId);
//
//        // assert
//        assertEquals(balance, result);
//    }
}

