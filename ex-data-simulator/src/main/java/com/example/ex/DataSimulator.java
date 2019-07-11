package com.example.ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

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
@EnableScheduling
public class DataSimulator {
    
    public static void main(String[] args) {
        SpringApplication.run(DataSimulator.class, args);
    }
    
}