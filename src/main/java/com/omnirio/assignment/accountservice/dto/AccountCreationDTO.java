package com.omnirio.assignment.accountservice.dto;

import java.time.LocalDate;

import com.omnirio.assignment.accountservice.domain.types.AccountType;

import lombok.Data;

@Data
public class AccountCreationDTO {

		private AccountType accType;
		private LocalDate openDate;
		private String customerId;
		private String customerName;
		private String branch;
		private boolean isMinor;
		private UserDTO userDetails;
}