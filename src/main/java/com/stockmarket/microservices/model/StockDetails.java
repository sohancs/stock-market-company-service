package com.stockmarket.microservices.model;

import java.util.List;

import lombok.Data;

@Data
public class StockDetails {
	private String companyCode;
	private List<Double> stockPrice;
}
