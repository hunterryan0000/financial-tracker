package com.etse.ft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@EnableJdbcRepositories(basePackages = "com.etse.ft.Repository")
@SpringBootApplication
public class FtApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtApplication.class, args);
	}

}
