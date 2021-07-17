package com.stockmarket.microservices.mapper;

import org.springframework.stereotype.Component;

import com.stockmarket.microservices.model.Company;
import com.stockmarket.microservices.model.CompanyRequestDTO;
import com.stockmarket.microservices.model.CompanyResponseDTO;
import com.stockmarket.microservices.model.StockExchangeEnum;

@Component
public class CompanyMapper {
	
	public CompanyResponseDTO mapCompanyEntityToDto(Company company) {
		CompanyResponseDTO responseDTO = new CompanyResponseDTO();
		responseDTO.setCompanyCode(company.getCompanyCode());
		responseDTO.setCompanyName(company.getCompanyName());
		responseDTO.setCompanyCeo(company.getCompanyCeo());
		responseDTO.setTurnOver(company.getTurnOver());
		responseDTO.setStockExchange(StockExchangeEnum.valueOf(company.getStockExchange()));
		responseDTO.setCompanyWebsite(company.getCompanyWebsite());
		return responseDTO;
	}
	
	public Company mapCompanyRequestDtoToEntity(CompanyRequestDTO requestDto) {
		Company company = new Company();
		company.setCompanyCode(requestDto.getCompanyCode());
		company.setCompanyName(requestDto.getCompanyName());
		company.setCompanyCeo(requestDto.getCompanyCeo());
		company.setTurnOver(requestDto.getTurnOver());
		company.setStockExchange(requestDto.getStockExchange().name());
		company.setCompanyWebsite(requestDto.getCompanyWebsite());
		return company;
	}
}
