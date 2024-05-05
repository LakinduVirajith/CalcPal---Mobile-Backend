package com.calcpal.practognosticdiagnosisservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PractognosticDiagnosisServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PractognosticDiagnosisServiceApplication.class, args);
	}

}
