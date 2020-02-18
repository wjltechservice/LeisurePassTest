package com.wjltechservices.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ComponentScan(basePackages = "com.*")
@PropertySource("classpath:cucumber.properties")
public class TestConfig {
}
