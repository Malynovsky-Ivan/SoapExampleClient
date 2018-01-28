package com.soapexample;

import com.soapexample.consumer.FirstEndpointClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SoapexampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapexampleApplication.class, args);
	}

	@Bean
	CommandLineRunner lookup(FirstEndpointClient client) {
		client.doStuff();

		return args -> {};
	}
}
