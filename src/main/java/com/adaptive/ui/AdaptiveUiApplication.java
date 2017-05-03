package com.adaptive.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdaptiveUiApplication {

	public static void main(String[] args) {
		//运行程序
		SpringApplication springApplication = new SpringApplication(AdaptiveUiApplication.class);
		springApplication.run(args);
	}
}
