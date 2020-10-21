package com.omnirio.assignment.accountservice.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.omnirio.assignment.accountservice.domain.types.AccountType;

import lombok.Data;

@Data
public class AccountDetailsDto extends RepresentationModel<AccountDetailsDto> {

	private AccountType accType;
	private LocalDate openDate;
	private String customerId;
	private String customerName;
	private String branch;
	private boolean isMinor;

}
