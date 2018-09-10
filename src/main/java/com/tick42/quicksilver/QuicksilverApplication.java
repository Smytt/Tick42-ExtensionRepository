package com.tick42.quicksilver;

import com.tick42.quicksilver.services.base.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class QuicksilverApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuicksilverApplication.class, args);
    }
}
