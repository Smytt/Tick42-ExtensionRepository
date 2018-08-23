package com.tick42.quicksilver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.File;
import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.models.User;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.SessionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Tag.class)
                .addAnnotatedClass(File.class)
                .buildSessionFactory();
    }

    @Bean
    public HttpClient createHttpClient() {
        return HttpClientBuilder.create().build();
    }

    @Bean
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }
}
