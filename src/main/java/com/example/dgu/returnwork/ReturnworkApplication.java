package com.example.dgu.returnwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan("com.example.dgu.returnwork.global.properties")
@EnableScheduling
public class ReturnworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReturnworkApplication.class, args);
	}

}
