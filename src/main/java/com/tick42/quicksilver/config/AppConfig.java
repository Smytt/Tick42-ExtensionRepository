package com.tick42.quicksilver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tick42.quicksilver.models.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.SessionFactory;
import org.kohsuke.github.GitHub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

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
                .addAnnotatedClass(GitHubModel.class)
                .buildSessionFactory();
    }

    @Bean
    public GitHub createGitHub() {
        try {
            return GitHub.connect("Smytt", "5c1a77eec3047ae6b562a55a7c0e4d4735cb38ef");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
