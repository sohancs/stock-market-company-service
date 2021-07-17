package com.stockmarket.microservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.stockmarket.microservices.model.Company;

@Repository
public interface ManageCompanyRepository extends JpaRepository<Company, Long> {

	Optional<Company> findByCompanyCode(String companyCode);
	
	List<Company> findAll();
	
	Integer deleteByCompanyCode(String companyCode);

}
