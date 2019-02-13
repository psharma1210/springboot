package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulServerProxyAndRoutingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServerProxyAndRoutingApplication.class, args);
	}

}

