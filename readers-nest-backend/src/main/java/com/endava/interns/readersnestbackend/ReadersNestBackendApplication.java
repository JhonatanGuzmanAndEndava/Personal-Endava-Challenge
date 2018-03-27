package com.endava.interns.readersnestbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.endava.interns.readersnestbackend.books.persistence"})
public class ReadersNestBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadersNestBackendApplication.class, args);
	}
}
