package com.example.TrafficBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TrafficBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrafficBoardApplication.class, args);
	}

}
