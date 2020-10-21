package com.omnirio.assignment.accountservice.controller.exceptions;

public class CustomerNotFoundException extends RuntimeException{

		private static final long serialVersionUID = 1L;

		public CustomerNotFoundException(String exception) {
			super(exception);
		}
	}
