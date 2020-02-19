package com.wjltechservices.stepdefinitions;

import com.wjltechservices.config.TestConfig;
import com.wjltechservices.context.Context;
import com.wjltechservices.context.ScenarioContext;
import com.wjltechservices.utilities.VendorUtility;
import io.cucumber.core.gherkin.vintage.internal.gherkin.deps.com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes =  TestConfig.class)
public class VendorStepDefinitions {

    private final VendorUtility vendorUtility;
    private final ScenarioContext scenarioContext;

    @Autowired
    public VendorStepDefinitions(VendorUtility vendorUtility, ScenarioContext scenarioContext) {
        this.vendorUtility = vendorUtility;
        this.scenarioContext = scenarioContext;
    }

    @When("^I add a new vendor called (.*)$")
    public void iAddANewVendorCalledVendor(String vendorName) {
        String vendorId = vendorUtility.addNewVendor(vendorName);
        scenarioContext.putContext(Context.VENDOR_ID, vendorId);
    }

    @Given("^I have a vendor in the system called (.*)$")
    public void iHaveAVendorInTheSystemCalledVendor(String vendorName) {
        vendorUtility.addNewVendor(vendorName);
    }

    @Then("^(\\w+) is returned as the unique vendor ID$")
    public void vendorIDIsReturnedAsTheUniqueVendorID(String vendorId) {
        String createdVendorId = (String) scenarioContext.getContext(Context.VENDOR_ID);

        assertThat(createdVendorId, is(vendorId));
    }

    @When("^I query for the vendor using the ID (\\w+)$")
    public void iQueryForTheVendorUsingTheIDVendorID(String vendorId) {
        String details = vendorUtility.getVendorDetails(vendorId);

        scenarioContext.putContext(Context.VENDOR_DETAILS, details);
    }

    @SuppressWarnings("unchecked")
    @Then("^I am provided with the details (.*)$")
    public void iAmProvidedWithTheVendorDetails(String details) {
        Map<String, Object> expected = (Map<String, Object>) new Gson().fromJson(details, Map.class);

        String result = (String) scenarioContext.getContext(Context.VENDOR_DETAILS);

        Map<String, Object> resultMap = (Map<String, Object>) new Gson().fromJson(result, Map.class);
        assertThat(resultMap, is(expected));
    }
}
