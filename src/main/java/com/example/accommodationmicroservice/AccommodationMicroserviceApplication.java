package com.example.accommodationmicroservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableRabbit
public class AccommodationMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccommodationMicroserviceApplication.class, args);
	}

}
