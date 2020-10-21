package com.omnirio.assignment.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omnirio.assignment.accountservice.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String>{

	
}
