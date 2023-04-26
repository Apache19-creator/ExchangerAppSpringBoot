package com.herotech.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.herotech.**")
@EntityScan(basePackages = "com.herotech.**")
@EnableJpaRepositories(basePackages = "com.herotech.**")
public class XchangerApplication {
    public static void main(String[] args) {
        SpringApplication.run(XchangerApplication.class, args);
    }
}