package com.stockmarket.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.stockmarket.microservices.exception.FeignExceptionDecoder;

import feign.codec.ErrorDecoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories(basePackages = "com.stockmarket.microservices.*")
@EnableFeignClients(basePackages = "com.stockmarket.microservices")
@EnableSwagger2
public class ManageCompanyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageCompanyServiceApplication.class, args);
	}
	
	@Bean
    public ErrorDecoder errorDecoder() {
        return new FeignExceptionDecoder();
    }

}
