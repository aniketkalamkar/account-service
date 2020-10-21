package com.omnirio.assignment.accountservice.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.omnirio.assignment.accountservice.controller.exceptions.CustomerNotFoundException;
import com.omnirio.assignment.accountservice.domain.Account;
import com.omnirio.assignment.accountservice.dto.AccountCreationDTO;
import com.omnirio.assignment.accountservice.dto.AccountDetailsDto;
import com.omnirio.assignment.accountservice.dto.UserOutputDTO;
import com.omnirio.assignment.accountservice.repository.AccountRepository;
import com.omnirio.assignment.accountservice.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	RestTemplate restTemplate;
	@Override
	public Optional<Account> getAccountDetails(String accountId) {
		return accountRepository.findById(accountId);
	}

	@Override
	
	public Account createAccount(AccountCreationDTO accountdto, String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);;

		UserOutputDTO user =null;
		
		ResponseEntity<UserOutputDTO> userResponse=null;
		
		if(accountdto.getCustomerId()==null ){
			
			HttpEntity<?> entity = new HttpEntity<>(accountdto.getUserDetails(), headers);
			 userResponse  = restTemplate.exchange("http://customer-microservice/customer-service/api/user", HttpMethod.POST, entity, UserOutputDTO.class);
		}else {
			HttpEntity<?> entity = new HttpEntity<>("", headers);
			userResponse  = restTemplate.exchange("http://customer-microservice/customer-service/api/user/"+accountdto.getCustomerId(), 
					HttpMethod.GET, entity, UserOutputDTO.class);
		}

		if(userResponse.getStatusCode().is2xxSuccessful())
			user = userResponse.getBody();			
		else
			throw new CustomerNotFoundException("Customer not found ");
		Account account = convertToAccount(accountdto);
		account.setCustomerId(user.getUserId());
		account.setMinor(isMinorAccount(user.getDateOfBirth()));
		return accountRepository.save(account);
	}

	private boolean isMinorAccount(LocalDate dateOfBirth) {
		return Period.between(dateOfBirth, LocalDate.now()).getYears()<18;
	}
	
	@Override
	public Optional<Account> updateAccount(String accountId, AccountDetailsDto accountdto) {
		Optional<Account> accountOpt=accountRepository.findById(accountId);
		if(accountOpt.isPresent()){
			Account account = accountOpt.get();
			account.setAccType(accountdto.getAccType());
			account.setBranch(accountdto.getBranch());
			account.setCustomerName(accountdto.getCustomerName());		
			return Optional.of(accountRepository.save(account));
		}
		return Optional.empty();
	}

	@Override
	public void deleteAccount(String accountId) {
		accountRepository.deleteById(accountId);
	} 

	private Account convertToAccount(AccountCreationDTO accountDTO) {
		Account account= modelMapper.map(accountDTO, Account.class);
	    return account;
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();		
	}
}
