package com.stockmarket.microservices.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.stockmarket.microservices.exception.CompanyAlreadyExistsException;
import com.stockmarket.microservices.exception.CompanyNotFoundException;
import com.stockmarket.microservices.exception.StockNotFoundException;
import com.stockmarket.microservices.model.ErrorDetails;

import feign.FeignException;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	private Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOGGER.error("MethodArgumentNotValidException occurred ");

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> {
			return error.getField() + " : " + error.getDefaultMessage();
		}).collect(Collectors.toList());

		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage(),
				MethodArgumentNotValidException.class.getSimpleName(), errors);
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		LOGGER.error("Exception occurred in ManageCompany Service ", ex);

		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
				Exception.class.getSimpleName(), null);
		return new ResponseEntity<Object>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(StockNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleStockNotFoundException(StockNotFoundException ex) {
		LOGGER.error("StockNotFoundException occurred in ManageCompany Service ", ex);

		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, ex.getMessage(),
				StockNotFoundException.class.getSimpleName(), null);
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleCompanyNotFoundException(CompanyNotFoundException ex) {
		LOGGER.error("CompanyNotFoundException occurred in ManageCompany Service ", ex);

		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, ex.getMessage(),
				CompanyNotFoundException.class.getSimpleName(), null);
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CompanyAlreadyExistsException.class)
	public ResponseEntity<ErrorDetails> handleCompanyAlreadyExistsException(CompanyAlreadyExistsException ex) {
		LOGGER.error("CompanyAlreadyExistsException occurred in ManageCompany Service ", ex);

		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FOUND, ex.getMessage(),
				CompanyAlreadyExistsException.class.getSimpleName(), null);
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.FOUND);
	}

	/*
	 * @ExceptionHandler(FeignException.class) public ResponseEntity<ErrorDetails>
	 * handleFeignStatusException(FeignException ex, HttpServletResponse response) {
	 * HttpStatus status = HttpStatus.valueOf(ex.status());
	 * LOGGER.error("FeignException occurred in ManageCompany Service {}, {}",
	 * status, ex);
	 * 
	 * ErrorDetails errorDetails = new ErrorDetails(status, ex.contentUTF8(),
	 * FeignException.class, null); return new
	 * ResponseEntity<ErrorDetails>(errorDetails, status); }
	 */

}
