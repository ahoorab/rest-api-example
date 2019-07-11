package com.example.ex.repository.admin;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Test class to set spring configuration for persistence
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.example.ex.jpa.repository")
@EntityScan("com.example.ex.model")
public class TestJpaConfig {

}
