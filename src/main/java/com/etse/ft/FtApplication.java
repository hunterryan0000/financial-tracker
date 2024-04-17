package com.etse.ft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration //can be used by the Spring IoC container as a source of bean definitions.
@ComponentScan(basePackages = {"com.etse.ft"}) // enables Spring to find and register all the components
@EnableJpaRepositories(basePackages = {"com.etse.ft.Repository"}) // Make sure this points to your repository package
@SpringBootApplication //provides a simple way to start up a Spring application context.
@EnableTransactionManagement //Place it on a configuration class to have Spring manage transactions
public class FtApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtApplication.class, args);
	}

}
