package org.nobleson.financialtransactionprocessingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableTransactionManagement
@SpringBootApplication
public class FinancialTransactionProcessingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialTransactionProcessingSystemApplication.class, args);
	}

}
