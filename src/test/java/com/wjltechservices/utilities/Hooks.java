package com.wjltechservices.utilities;

import com.wjltechservices.config.TestConfig;
import com.wjltechservices.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

/**
 * Our hooks into cucumber, allowing us to run code before and after each scenario
 */
public class Hooks {

    @Value("${application.root}") private String baseUri;
    @Value("${application.port}") private String port;

    private final ScenarioContext scenarioContext;

    @Autowired
    public Hooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Before
    public void setUp(Scenario scenario) {
        RestAssured.baseURI = baseUri;
        RestAssured.port = Integer.parseInt(port);
    }

    @After
    public void tearDown(Scenario scenario) {
        scenarioContext.clearContext();
    }
}
