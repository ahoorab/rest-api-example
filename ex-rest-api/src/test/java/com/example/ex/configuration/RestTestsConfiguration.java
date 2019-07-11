package com.example.ex.configuration;

import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@TestPropertySource("classpath:application-test.properties")
@RunWith(SpringRunner.class)
public abstract class RestTestsConfiguration {

}
