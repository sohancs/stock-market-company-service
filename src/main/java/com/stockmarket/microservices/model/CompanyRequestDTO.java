package com.stockmarket.microservices.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDTO {
	@NotBlank(message = "Company Code is required.")
	private String companyCode;
	@NotBlank(message = "Company Name is required.")
	private String companyName;
	@NotBlank(message = "Company CEO name is required.")
	private String companyCeo;
	@NotNull(message = "TurnOver is required.")
	@DecimalMin(value = "100000000", message = "TurnOver must be greater than 10 Crores.")
	@Digits(message = "TurnOver should be rounded upto 2 digits. ex: 10,00,00,000.12", integer = 11, fraction = 2)
	private BigDecimal turnOver;
	@NotNull(message = "Stock Exchange option is required.")
	private StockExchangeEnum stockExchange;
	@NotBlank(message = "Company Website is required.")
	@Email(message = "Email should be in correct format.")
	private String companyWebsite;
}
