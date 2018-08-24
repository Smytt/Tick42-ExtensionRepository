package com.tick42.quicksilver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuicksilverApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuicksilverApplication.class, args);
	}

	// 5c1a77eec3047ae6b562a55a7c0e4d4735cb38ef
}
