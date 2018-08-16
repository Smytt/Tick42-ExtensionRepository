package com.tick42.quicksilver.config;

import com.tick42.quicksilver.models.Extension;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class AppConfig {

    @Bean
    public SessionFactory createSessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Extension.class)
                .buildSessionFactory();
    }
}