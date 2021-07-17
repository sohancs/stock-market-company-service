package com.stockmarket.microservices.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CompanyResponseDTO {
	private String companyCode;
	private String companyName;
	private String companyCeo;
	private BigDecimal turnOver;
	private StockExchangeEnum stockExchange;
	private String companyWebsite;
	private Double latestStockPrice;
}
