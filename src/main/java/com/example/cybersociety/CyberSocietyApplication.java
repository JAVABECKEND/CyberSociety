package com.example.cybersociety;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "com.*")
@CrossOrigin("http://localhost:8081")
public class CyberSocietyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyberSocietyApplication.class, args);
    }

}
