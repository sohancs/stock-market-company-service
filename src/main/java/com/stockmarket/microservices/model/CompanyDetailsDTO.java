package com.stockmarket.microservices.model;

import java.util.List;

import lombok.Data;

@Data
public class CompanyDetailsDTO {
	List<CompanyResponseDTO> companiesDetails;
}
