package com.wjltechservices.stepdefinitions;

import com.wjltechservices.config.TestConfig;
import com.wjltechservices.context.Context;
import com.wjltechservices.context.ScenarioContext;
import com.wjltechservices.utilities.CustomerUtility;
import io.cucumber.core.gherkin.vintage.internal.gherkin.deps.com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CustomerStepDefinitions {

    private final CustomerUtility customerUtility;
    private final ScenarioContext scenarioContext;

    @Autowired
    public CustomerStepDefinitions(CustomerUtility customerUtility, ScenarioContext scenarioContext) {
        this.customerUtility = customerUtility;
        this.scenarioContext = scenarioContext;
    }

    @When("^I add a new customer (.*) from (.*)$")
    public void iAddANewCustomerFullNameFromHomeCity(String fullName, String homeCity) {
        Long customerId = customerUtility.addCustomer(fullName, homeCity);
        scenarioContext.putContext(Context.CUSTOMER_ID, customerId);
    }

    @Then("^I am returned a numeric Customer ID$")
    public void iAmReturnedANumericCustomerID() {
        Object customerId = scenarioContext.getContext(Context.CUSTOMER_ID);

        assertThat(customerId, is(notNullValue()));
        assertThat(customerId, is(instanceOf(Number.class)));
    }

    @Given("^I have a customer in the system (.*) from (.*)$")
    public void iHaveACustomerInTheSystemFullNameFromHomeCity(String fullName, String homeCity) {
        Long customerId = customerUtility.addCustomer(fullName, homeCity);
        scenarioContext.putContext(Context.CUSTOMER_ID, customerId);
    }

    @Then("^I am returned the Customer ID for the existing customer$")
    public void iAmReturnedTheCustomerIDForTheExistingCustomer() {
        assertThat(scenarioContext.getContext(Context.CUSTOMER_ID), is(notNullValue()));
    }

    @When("^I query for the customer using their Customer ID$")
    public void iQueryForTheCustomerUsingTheirCustomerID() {
        Long customerId = (Long) scenarioContext.getContext(Context.CUSTOMER_ID);

        String customerDetails = customerUtility.getCustomer(customerId);
        scenarioContext.putContext(Context.CUSTOMER_DETAILS, customerDetails);
    }

    @SuppressWarnings("unchecked")
    @Then("^I am provided with the Customer Details (.*)$")
    public void iAmProvidedWithTheCustomerDetailsCustomerDetails(String customerDetails) {
        Long customerId = (Long) scenarioContext.getContext(Context.CUSTOMER_ID);
        customerDetails = customerDetails.replaceAll("<GENERATED>", String.valueOf(customerId));
        Map<String, Object> expected = (Map<String, Object>) new Gson().fromJson(customerDetails, Map.class);

        String result = (String) scenarioContext.getContext(Context.CUSTOMER_DETAILS);
        Map<String, Object> resultMap = (Map<String, Object>) new Gson().fromJson(result, Map.class);

        assertThat(resultMap, is(expected));
    }
}
