package com.clonebose.bose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoseApplication.class, args);
	}

}
