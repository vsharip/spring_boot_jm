package ru.sharipov.spring.spring_boot_jm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringBootJmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJmApplication.class, args);
    }
}
