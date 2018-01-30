package com.soapexample;

import com.soapexample.consumer.DocumentsClient;
import com.soapexample.consumer.FirstEndpointClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SoapexampleApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = SpringApplication.run(SoapexampleApplication.class, args);
		DocumentsClient documentsClient = context.getBean(DocumentsClient.class);
		String searchWord = readSize();
		int size = searchWord.length();
		while (size > 0) {
			documentsClient.storeDocument(searchWord);
			size = readSize().length();
		}
		System.out.println("exit");
	}
	private static String readSize() throws IOException {
		String line = null;
		while (line == null) {
			System.out.printf("\nPlease enter the word need to find in document or just press enter to exit: ");
			line = readLine();
			if (line == null || line.trim().isEmpty()) {
				return "Plese enter the word!";
			}
		}
		return line;
	}

	private static String readLine() throws IOException {
		return new BufferedReader(new InputStreamReader(System.in)).readLine();
	}

	/*@Bean
	CommandLineRunner lookup(FirstEndpointClient client, DocumentsClient documentsClient) {
		client.doStuff();

		return args -> {};
	}*/
}
