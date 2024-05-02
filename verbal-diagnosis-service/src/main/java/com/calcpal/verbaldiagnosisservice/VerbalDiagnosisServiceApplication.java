package com.calcpal.verbaldiagnosisservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Tag(name = "Greeting Controller")
public class VerbalDiagnosisServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerbalDiagnosisServiceApplication.class, args);
	}

	@Operation(summary = "Check Application Status", description = "Check if the application is running.")
	@GetMapping("/")
	public String testMessage(){
		return "verbal diagnosis microservice application is running smoothly";
	}

}
