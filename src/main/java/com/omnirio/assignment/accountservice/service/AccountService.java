package com.omnirio.assignment.accountservice.service;

import java.util.List;
import java.util.Optional;

import com.omnirio.assignment.accountservice.domain.Account;
import com.omnirio.assignment.accountservice.dto.AccountCreationDTO;
import com.omnirio.assignment.accountservice.dto.AccountDetailsDto;

public interface AccountService {

	public Optional<Account> getAccountDetails(String accountId);
	
	public List<Account> findAll();
	
	public Optional<Account>  updateAccount(String accountId,AccountDetailsDto accountdto);
	
	public void deleteAccount(String accountId);

	public Account createAccount(AccountCreationDTO accountdto, String token);
	
}
