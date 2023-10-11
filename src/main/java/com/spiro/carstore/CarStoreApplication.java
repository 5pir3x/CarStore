package com.spiro.carstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarStoreApplication.class, args);
	}

}
