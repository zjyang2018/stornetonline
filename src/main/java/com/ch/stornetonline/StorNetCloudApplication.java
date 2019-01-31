package com.ch.stornetonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class StorNetCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorNetCloudApplication.class, args);
	}
}
