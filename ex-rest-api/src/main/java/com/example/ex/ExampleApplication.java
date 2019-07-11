package com.example.ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot main class
 * 
 * Run with java -jar <jar including this class>
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@ComponentScan("com.example.ex")
@SpringBootApplication
@EntityScan("com.example.ex.model")
@EnableAutoConfiguration
@EnableCaching
public class ExampleApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }
    
}