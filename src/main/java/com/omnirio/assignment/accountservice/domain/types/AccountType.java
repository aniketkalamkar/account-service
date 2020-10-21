package com.omnirio.assignment.accountservice.domain.types;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AccountType {
	SAVINGS("S"), CURRENT("C"), CASH_CREDIT("CC"), LOAN("L");

	@Override
	public String toString() {
		return code;
	}

	private String code;

	private AccountType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
//	@JsonCreator
//	public static AccountType fromText(String text) {
//		for (AccountType r : AccountType.values()) {
//			if (r.getCode().equals(text)) {
//				return r;
//			}
//		}
//		throw new IllegalArgumentException();
//	}
}
