package com.omnirio.assignment.accountservice.dto;

import java.time.LocalDate;

import com.omnirio.assignment.accountservice.domain.types.Gender;

import lombok.Data;

@Data
public class UserDTO {

	private String userName;
	private LocalDate dateOfBirth;
	private Gender gender;
	private String phoneNo;	
	private String roleId;
	
}