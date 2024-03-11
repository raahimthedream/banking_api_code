package com.banking.api.bankin_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.api.bankin_project.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
