package com.etse.ft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableJdbcRepositories(basePackages = "com.etse.ft.Repository")
@ComponentScan(basePackages = {"com.etse.ft"}) // Adjust as needed
@EnableJpaRepositories(basePackages = {"com.etse.ft.Repository"}) // Make sure this points to your repository package
@SpringBootApplication
@EnableTransactionManagement
public class FtApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtApplication.class, args);
	}

}
