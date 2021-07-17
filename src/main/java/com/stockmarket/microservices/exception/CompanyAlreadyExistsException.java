package com.stockmarket.microservices.exception;

public class CompanyAlreadyExistsException extends RuntimeException {

	public CompanyAlreadyExistsException(String message) {
		super(message);
	}	
}
