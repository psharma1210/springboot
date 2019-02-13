package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoWarApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoWarApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DemoWarApplication.class);
	}	

	@RequestMapping(value="/")
	public String defaultView() {
		return "Started application deploy in Tomcat";
	}
	
	@Value("${spring.application.name}")
	String name;
	
	//with the default value
	@Value("${spring.last.name:xyz}")
	String lastName;
	
	@RequestMapping(value="/api/")
	public String readValueFromPropertiesFile() {
		return name+"  "+lastName;
	}
}

