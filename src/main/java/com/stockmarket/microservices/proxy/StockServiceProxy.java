package com.stockmarket.microservices.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.stockmarket.microservices.exception.CompanyNotFoundException;
import com.stockmarket.microservices.exception.StockNotFoundException;

@FeignClient(name = "stock-service", path = "/api/v1.0/market/stock")
public interface StockServiceProxy {

	@GetMapping("/getLatestStockPrice/{companycode}")
	public ResponseEntity<Object> fetchLatestStockPrices(@PathVariable("companycode") String companycode)
			throws StockNotFoundException;

	@GetMapping("/getAllLatestStockPrice")
	public ResponseEntity<Object> getAllLatestStockPrice();

	@DeleteMapping("/delete/{companyCode}")
	public ResponseEntity<Object> deleteStockDetails(@PathVariable("companyCode") String companyCode)
			throws StockNotFoundException;

}
