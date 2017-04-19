package com.adaptive.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdaptiveUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdaptiveUiApplication.class, args);
	}
}
