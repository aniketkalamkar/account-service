package com.omnirio.assignment.accountservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omnirio.assignment.accountservice.domain.Account;
import com.omnirio.assignment.accountservice.dto.AccountCreationDTO;
import com.omnirio.assignment.accountservice.dto.AccountDetailsDto;
import com.omnirio.assignment.accountservice.security.SecurityConstants;
import com.omnirio.assignment.accountservice.service.AccountService;
import com.omnirio.assignment.accountservice.service.impl.AccountServiceImpl;

@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping("/{accountId}")
	public ResponseEntity<AccountDetailsDto> getAccountDetailsById(@PathVariable String accountId){
		Optional<Account> account = accountService.getAccountDetails(accountId);
		AccountDetailsDto accountDetails=null;
		if(account.isPresent())
			 accountDetails= convertToAccountDTO(account.get());
		else
			return new ResponseEntity<AccountDetailsDto>(HttpStatus.NOT_FOUND);
		
		accountDetails.add(linkTo(methodOn( AccountController.class).getAccountDetailsById(accountId)).withSelfRel());
		return new ResponseEntity<AccountDetailsDto>(accountDetails,HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<AccountDetailsDto> createUser(@RequestBody AccountCreationDTO accountdto, @RequestHeader(name = SecurityConstants.HEADER_NAME) String token){
		Account account= accountService.createAccount(accountdto,token);
		AccountDetailsDto userOutput = convertToAccountDTO(account); 
		userOutput.add(linkTo(methodOn( AccountController.class).getAccountDetailsById(account.getAccountId())).withSelfRel());
		return new ResponseEntity<AccountDetailsDto>(userOutput,HttpStatus.CREATED);
	}
	
	@PutMapping("/{accountId}")
	public ResponseEntity<AccountDetailsDto> updateUser(@RequestBody AccountDetailsDto accountdto, @PathVariable String accountId){
		Optional<Account> account = accountService.updateAccount(accountId, accountdto);
		AccountDetailsDto accountOutput =null;
		if(account.isPresent())
			accountOutput = convertToAccountDTO(account.get());
		else
			return new ResponseEntity<AccountDetailsDto>(HttpStatus.NOT_FOUND);
		
		accountOutput.add(linkTo(methodOn( AccountController.class).getAccountDetailsById(accountId)).withSelfRel());
		return new ResponseEntity<AccountDetailsDto>(accountOutput,HttpStatus.OK);
	}
	
	@DeleteMapping("/{accountId}")
	public ResponseEntity<?> deleteUser(@PathVariable String accountId){
		accountService.deleteAccount(accountId);
		return ResponseEntity.noContent().build();
	}	
	
	private AccountDetailsDto convertToAccountDTO(Account account) {
		AccountDetailsDto accountDTO= modelMapper.map(account, AccountDetailsDto.class);
	    return accountDTO;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<AccountDetailsDto>> getAllAccountDetails(){
		List<Account> accounts = accountService.findAll();
		List<AccountDetailsDto> accDTOList = accounts.stream().map(this::convertToAccountDTO).collect(Collectors.toList());
		return new ResponseEntity<List<AccountDetailsDto>>(accDTOList, HttpStatus.OK);
	}
}