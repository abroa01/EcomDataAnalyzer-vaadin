package com.acs560.dataanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.acs560.dataanalyzer",
    "com.acs560.dataanalyzer.controller",
    "com.acs560.dataanalyzer.models",
    "com.acs560.dataanalyzer.repositories",
    "com.acs560.dataanalyzer.services",
    "com.acs560.dataanalyzer.services.impl"
})
public class EcomSalesAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomSalesAnalyzerApplication.class, args);
	}

}
