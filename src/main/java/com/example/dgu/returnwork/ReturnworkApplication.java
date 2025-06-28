package com.example.dgu.returnwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ReturnworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReturnworkApplication.class, args);
	}

}
