package com.stockmarket.microservices.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Optional;
import com.stockmarket.microservices.exception.CompanyAlreadyExistsException;
import com.stockmarket.microservices.exception.CompanyNotFoundException;
import com.stockmarket.microservices.exception.StockNotFoundException;
import com.stockmarket.microservices.mapper.CompanyMapper;
import com.stockmarket.microservices.model.Company;
import com.stockmarket.microservices.model.CompanyDetailsDTO;
import com.stockmarket.microservices.model.CompanyRequestDTO;
import com.stockmarket.microservices.model.CompanyResponseDTO;
import com.stockmarket.microservices.proxy.StockServiceProxy;
import com.stockmarket.microservices.repository.ManageCompanyRepository;

@Service
public class ManageCompanySevice {
	@Autowired
	private ManageCompanyRepository repo;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private StockServiceProxy proxy;

	private Logger LOGGER = LoggerFactory.getLogger(ManageCompanySevice.class);

	public boolean registerCompany(CompanyRequestDTO requestDto) throws CompanyAlreadyExistsException {
		LOGGER.info("inside registerCompany method of ManageCompanySevice ");
		LOGGER.info("CompanyRequestDTO - {}", requestDto);
		Company company = companyMapper.mapCompanyRequestDtoToEntity(requestDto);
		if (!isCompanyAlreadyExists(requestDto)) {
			LOGGER.info("Company not registered in our DB. Registering new company with code "
					+ requestDto.getCompanyCode());
			repo.save(company);
			LOGGER.info("Company has been registered successfully.");
			return true;
		} else {
			LOGGER.error("Company already exists with comapny-code " + requestDto.getCompanyCode());
			throw new CompanyAlreadyExistsException("Company is already exist in our DB.");
		}
	}

	private boolean isCompanyAlreadyExists(CompanyRequestDTO requestDto) {
		Optional<Company> companyOptional = repo.findByCompanyCode(requestDto.getCompanyCode());

		if (companyOptional.isPresent())
			return true;

		return false;
	}

	public CompanyResponseDTO fetchCompanyDetails(String companyCode) throws CompanyNotFoundException {
		LOGGER.info("Fetching company details for company-code : {}", companyCode);
		CompanyResponseDTO responseDTO = new CompanyResponseDTO();
		Optional<Company> companyOptional = repo.findByCompanyCode(companyCode);

		if (companyOptional.isPresent()) {
			responseDTO = companyMapper.mapCompanyEntityToDto(companyOptional.get());
			try {
				ResponseEntity<Object> stockPricesResponseEntity = proxy.fetchLatestStockPrices(companyCode);
				if (stockPricesResponseEntity.getStatusCode() == HttpStatus.OK) {
					responseDTO.setLatestStockPrice((Double) stockPricesResponseEntity.getBody());
				}
			} catch (StockNotFoundException ex) {
				LOGGER.error("Stock Details not available for company with comapny-code " + companyCode);
			}

		} else {
			LOGGER.error("Company not exists with comapny-code " + companyCode);
			throw new CompanyNotFoundException("Company not exists with comapny-code " + companyCode);
		}

		return responseDTO;
	}

	public CompanyDetailsDTO fetchAllCompaniesDetails() {
		LOGGER.info("inside fetchAllCompaniesDetails method");
		CompanyDetailsDTO companyDetailsDTO = new CompanyDetailsDTO();

		List<Company> companiesDetails = repo.findAll();
		Map<String, Double> latestPriceMap = null;

		try {
			// Fetch latest prices of stocks.
			ResponseEntity<Object> responseEntity = proxy.getAllLatestStockPrice();
			
			if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				latestPriceMap = (Map<String, Double>) responseEntity.getBody();
			}
		} catch (RuntimeException ex) {
			LOGGER.error("Stock Service unavailable ", ex.getCause());
		}

		if (!CollectionUtils.isEmpty(companiesDetails)) {
			/*
			 * List<CompanyResponseDTO> companyResponseDTO = companiesDetails.stream()
			 * .map(company -> companyMapper.mapCompanyEntityToDto(company))
			 * .collect(Collectors.toList());
			 */

			List<CompanyResponseDTO> resultDTO = new ArrayList<>();
			for (Company company : companiesDetails) {
				CompanyResponseDTO dto = companyMapper.mapCompanyEntityToDto(company);

				if (!CollectionUtils.isEmpty(latestPriceMap)) {
					dto.setLatestStockPrice(latestPriceMap.get(company.getCompanyCode()));
				}

				resultDTO.add(dto);
			}

			companyDetailsDTO.setCompaniesDetails(resultDTO);
		}

		return companyDetailsDTO;
	}

	@Transactional
	public boolean deleteCompanyDetails(String companyCode) throws CompanyNotFoundException, StockNotFoundException {
		LOGGER.info("deleteStockDetails for comany-code : {}", companyCode);

		Integer deletedRows = repo.deleteByCompanyCode(companyCode);
		ResponseEntity<Object> stockResponseEntity = null;
		try {
			stockResponseEntity = proxy.deleteStockDetails(companyCode);
		} catch (StockNotFoundException ex) {
			LOGGER.error("Stock service thrown exception ", ex.getCause());
		}

		if (deletedRows > 0) {
			return true;
		}

		throw new CompanyNotFoundException(
				"Company details for company-code " + companyCode + " does not exist or already deleted.");
	}

}
