package com.calcpal.sequentialdiagnosisservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SequentialDiagnosisServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SequentialDiagnosisServiceApplication.class, args);
	}

}
