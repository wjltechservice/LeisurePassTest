package com.wjltechservices.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        features = {"src/test/resources/com/wjltechservices/features"},
        glue = {"com.wjltechservices.stepdefinitions", "com.wjltechservices.utilities"})
@SpringBootTest
public class RunCucumberTest {
}
