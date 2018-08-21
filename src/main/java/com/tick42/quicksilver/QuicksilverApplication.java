package com.tick42.quicksilver;

import com.tick42.quicksilver.config.FileConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileConfig.class)
public class QuicksilverApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuicksilverApplication.class, args);
	}
}
