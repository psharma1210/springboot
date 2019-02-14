package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class AdminServerManageActuatorEndpointsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminServerManageActuatorEndpointsApplication.class, args);
	}

}

