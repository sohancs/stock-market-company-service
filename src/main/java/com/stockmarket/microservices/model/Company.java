package com.stockmarket.microservices.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "COMPANY")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Column(name = "COMPANY_CODE")
	private String companyCode;
	@Column(name = "COMPANY_NAME")
	private String companyName;
	@Column(name = "COMPANY_CEO")
	private String companyCeo;
	@Column(name = "COMPANY_WEBSITE")
	private String companyWebsite;
	@Column(name = "TURN_OVER")
	private BigDecimal turnOver;
	@Column(name = "STOCK_EXCHANGE")
	private String stockExchange;
	@Column(name = "CREATED_TIMESTAMP")
	private LocalDateTime createdTimestamp;
	@Column(name = "MODIFIED_TIMESTAMP")
	private LocalDateTime modifiedTimestamp;
	
	@PrePersist
	private void preSetCreatedTS() {
		this.createdTimestamp = LocalDateTime.now();
		this.modifiedTimestamp = LocalDateTime.now();
	}
	
	@PostUpdate
	private void postUpdateModifiedTS() {
		this.modifiedTimestamp = LocalDateTime.now();
	}
}
