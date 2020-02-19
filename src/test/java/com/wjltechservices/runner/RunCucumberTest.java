package com.wjltechservices.runner;

import com.wjltechservices.config.TestConfig;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        features = {"src/test/resources/com/wjltechservices/features"},
        glue = {"com.wjltechservices.stepdefinitions", "com.wjltechservices.utilities"})
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
public class RunCucumberTest {
}
