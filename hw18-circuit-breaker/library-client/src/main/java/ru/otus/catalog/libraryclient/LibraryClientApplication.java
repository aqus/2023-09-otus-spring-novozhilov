package ru.otus.catalog.libraryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LibraryClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryClientApplication.class, args);
	}

}
