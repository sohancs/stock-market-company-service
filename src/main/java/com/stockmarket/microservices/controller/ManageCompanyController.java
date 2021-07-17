package com.stockmarket.microservices.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.microservices.exception.CompanyAlreadyExistsException;
import com.stockmarket.microservices.exception.CompanyNotFoundException;
import com.stockmarket.microservices.exception.StockNotFoundException;
import com.stockmarket.microservices.model.CompanyDetailsDTO;
import com.stockmarket.microservices.model.CompanyRequestDTO;
import com.stockmarket.microservices.model.CompanyResponseDTO;
import com.stockmarket.microservices.service.ManageCompanySevice;

@RestController
@RequestMapping("/api/v1.0/market/company")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ManageCompanyController {

	@Autowired
	private ManageCompanySevice service;

	@PostMapping("/register")
	public ResponseEntity<Object> registerCompany(@RequestBody @Valid final CompanyRequestDTO requestDTO)
			throws CompanyAlreadyExistsException {
		service.registerCompany(requestDTO);
		return new ResponseEntity<Object>("Register Successfully...", HttpStatus.CREATED);
	}

	@GetMapping("/info/{companyCode}")
	public ResponseEntity<CompanyResponseDTO> fetchCompanyDetails(@PathVariable("companyCode") String companyCode)
			throws CompanyNotFoundException {
		CompanyResponseDTO responseDTO = service.fetchCompanyDetails(companyCode);

		return new ResponseEntity<CompanyResponseDTO>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<CompanyDetailsDTO> fetchAllCompaniesDetails() {
		CompanyDetailsDTO responseDTO = service.fetchAllCompaniesDetails();

		return new ResponseEntity<CompanyDetailsDTO>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{companyCode}")
	public ResponseEntity<Object> deleteStockDetails(@PathVariable("companyCode") String companyCode)
			throws CompanyNotFoundException, StockNotFoundException {
		service.deleteCompanyDetails(companyCode);

		return new ResponseEntity<Object>("Company Details deleted successfully", HttpStatus.NO_CONTENT);
	}

}
